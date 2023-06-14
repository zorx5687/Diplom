<script>
import {vMaska} from "maska";
import {VMoney} from 'v-money'
import axios from "axios";

export default {
    directives: {
        maska: vMaska,
        money: VMoney
    },
    beforeCreate() {
        this.emitter.emit('clearActive');
    },
    methods: {
        onPost() {
            if (!this.name.trim()) {
                this.nameError = "Поле должно быть заполнено";
            } else {
                this.nameError = "";
            }
            if (!this.address.trim()) {
                this.addressError = "Поле должно быть заполнено";
            } else {
                this.addressError = "";
            }
            if (!this.cat) {
                this.catError = "Поле должно быть заполнено";
            } else {
                this.catError = "";
            }
            if (!this.description.trim()) {
                this.descriptionError = "Поле должно быть заполнено";
            } else {
                this.descriptionError = "";
            }
            if (!this.file) {
                this.fileError = "Загрузите картинку";
            } else {
                this.fileError = "";
            }

            if (this.nameError === "" &&
                this.addressError === "" &&
                this.descriptionError === "" &&
                this.catError === "" &&
                this.fileError === "") {
                const form = new FormData();
                form.append("image", this.file)
                axios.post('https://api.imgbb.com/1/upload?key=3e7a5d93502894e501a9329f92149128', form,
                    {
                        onUploadProgress: uploadEvent => {
                            console.log("Progress: " + Math.round(uploadEvent.loaded / uploadEvent.total))
                        }
                    })
                    .then(res => {
                        console.log(res);
                        console.log(res.data.data);
                        console.log(res.data.url);
                        if (res.status !== 200) {
                            this.fileError = "Не удалось загрузить картину";
                        } else {
                            fetch("http://localhost:8080/api/v1/post", {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({

                                    title: this.name,
                                    category: this.cat,
                                    address: this.address,
                                    description: this.description,
                                    price: this.price,
                                    photoUrl: res.data.data.url,
                                    authorId: JSON.parse(localStorage.user).id

                                })
                            }).then(response => response.json())
                                .then(data => {
                                    if (data["message"]) {
                                        this.error = data["message"];
                                    } else {
                                        this.cat = "";
                                        this.name = "";
                                        this.address = "";
                                        this.description = "";
                                        this.price = 0;
                                        this.emitter.emit('onAddPost')
                                        this.$router.push("index");
                                    }
                                })
                                .catch(error => this.error = error);
                        }
                    });

            }

        },
        uploadPhoto() {
            console.log(this.$refs.f.files[0])
            console.log(JSON.stringify(this.$refs.f.files[0]));
            this.file = this.$refs.f.files[0];
            var p = document.getElementById("uploaded-photo");
            p.src = URL.createObjectURL(this.file);
            p.toggle('open');
        },
        dragover(e) {
            e.preventDefault();
            this.isDragging = true;
        },
        dragleave() {
            this.isDragging = false;
        },
        drop(e) {
            e.preventDefault();
            this.$refs.f.files = e.dataTransfer.files;
            this.uploadPhoto();
            this.isDragging = false;
        },
        remove() {
            this.file = null;
            var p = document.getElementById("uploaded-photo");
            p.src = "";
        }
    },
    props: ['categories'],
    data: function () {
        return {
            error: "",
            name: "",
            nameError: "",
            address: "",
            addressError: "",
            description: "",
            descriptionError: "",
            cat: "",
            catError: "",
            price: 0,
            file: null,
            fileError: "",
            isDragging: false,
            photo: "",
            money: {
                decimal: '',
                thousands: '',
                prefix: '₽ ',
                precision: 0,
                masked: false
            }
        }
    }

}
</script>

<template>
    <div class="writer-post">
        <div class="enter">
            <div class="form">
                <div class="header">Выложить товар</div>
                <form @submit.prevent="onPost">
                    <div class="data">
                        <div class="field">
                            <div class="name">
                                Название
                            </div>
                            <div class="place-holder">
                                <input type="text" v-model="name"/>
                            </div>
                        </div>
                        <div class="field error">{{ nameError }}</div>
                        <div class="field">
                            <div class="name">
                                Категория
                            </div>
                            <div class="place-holder">

                                <select v-model="cat">
                                    <option v-for="c in categories" :value="c">{{ c }}</option>
                                </select>
                            </div>
                        </div>
                        <div class="field error">{{ catError }}</div>
                        <div class="field">
                            <div class="name">
                                Адресс
                            </div>
                            <div class="place-holder">
                                <input type="text" v-model="address"/>
                            </div>
                        </div>
                        <div class="field error">{{ addressError }}</div>
                        <div class="field">
                            <div class="name">
                                Описание
                            </div>
                            <div class="place-holder">
                                <textarea type="text" v-model="description"/>
                            </div>
                        </div>
                        <div class="field error">{{ descriptionError }}</div>
                        <div class="field">
                            <div class="name">
                                Цена
                            </div>
                            <div class="place-holder">
                                <input v-model.lazy="price" v-money="money"/>
                            </div>
                        </div>
                        <div class="field">
                            <div class="name">
                                Загрузить фото
                            </div>
                            <img id="uploaded-photo">
                            <div class="place-holder">
                                <label for="product-photo" class="b-btn dropzone" @dragover="dragover"
                                       @dragleave="dragleave"
                                       @drop="drop">
                                    <span v-if="!isDragging">Прикрепите фото</span>
                                    <span v-else>Прикрепить!</span>

                                </label>
                                <input type="file" @change.prevent="uploadPhoto"
                                       accept="image/png, image/gif, image/jpeg" id="product-photo"
                                       ref="f"
                                />
                            </div>
                            <div v-if="file" class="added-phoot">
                                {{ file.name }}
                                <button
                                        class="ml-2"
                                        type="button"
                                        @click="remove"
                                        title="Remove file"
                                >
                                    <b>×</b>
                                </button>
                            </div>
                        </div>
                        <div class="field error">{{ fileError }}</div>
                    </div>
                    <div class="field error">{{ error }}</div>
                    <div class="send">
                        <input class="b-btn" type="submit" value="Отправить">
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
#uploaded-photo {
    max-height: 300px;
    max-width: 300px;
}
</style>