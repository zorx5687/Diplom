<script>
import {defineComponent} from 'vue'
import axios from "axios";
import {VMoney} from 'v-money'
import {vMaska} from "maska";

export default {
    directives: {
        maska: vMaska,
        money: VMoney
    },
    data: function () {
        return {
            post: null,
            url: "https://i.ibb.co/P55Cv8F/slon.jpg",
            isFlipped: false,
            file: null,
            photo: "",
            isDragging: false,
            money: {
                decimal: '',
                thousands: '',
                prefix: '₽ ',
                precision: 0,
                masked: false
            },
            titleError: "",
            addressError: "",
            catError: "",
            descriptionError: "",
            error: ""
        }
    },
    beforeMount() {
        this.emitter.emit('clearActive');
        let resuset = 'http://localhost:8080/api/v1/post/' + this.$route.params.id;
        axios.get(resuset)
            .then(response => {
                if (response.status === 200) {
                    this.post = response.data;
                }
            }).catch(e => this.post = {
            id: "",
            title: "",
            address: "",
            price: "",
            category: "",
            description: "",
            author: {
                id: "",
                phone: "",
                surname: "",
                name: ""
            }

        });
    },
    methods: {
        onAuthor() {
            this.$router.push(
                {
                    name: 'profile',
                    params: {
                        id: this.post.author.id
                    },
                });
        },
        onCat() {
            this.emitter.emit('onShowCat', this.post.category);
        },
        edit() {
            this.isFlipped = !this.isFlipped;
        },
        uploadPhoto() {
            console.log(this.$refs.f.files[0])
            console.log(JSON.stringify(this.$refs.f.files[0]));
            this.file = this.$refs.f.files[0];
            // var p = document.getElementById("uploaded-photo");
            // p.src = URL.createObjectURL(this.file);
            // p.toggle('open');
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
        },
        save() {
            if (!this.post.title.trim()) {
                this.titleError = "Поле должно быть заполнено";
            } else {
                this.titleError = "";
            }
            if (!this.post.address.trim()) {
                this.addressError = "Поле должно быть заполнено";
            } else {
                this.addressError = "";
            }
            if (!this.post.category) {
                this.catError = "Поле должно быть заполнено";
            } else {
                this.catError = "";
            }
            if (!this.post.description.trim()) {
                this.descriptionError = "Поле должно быть заполнено";
            } else {
                this.descriptionError = "";
            }
            if (this.titleError === "" &&
                this.addressError === "" &&
                this.descriptionError === "" &&
                this.catError === "") {
                if (this.file) {
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
                                fetch("http://localhost:8080/api/v1/post/update", {
                                    method: 'PATCH',
                                    headers: {
                                        'Content-Type': 'application/json'
                                    },
                                    body: JSON.stringify({
                                        postId: this.post.id,
                                        title: this.post.title,
                                        category: this.post.category,
                                        address: this.post.address,
                                        description: this.post.description,
                                        price: this.post.price,
                                        photoUrl: res.data.data.url,
                                        authorId: JSON.parse(localStorage.user).id
                                    })
                                }).then(response => response.json())
                                    .then(data => {
                                        if (data["message"]) {
                                            this.error = data["message"];
                                        } else {
                                            this.emitter.emit("onAddPost")
                                            // this.$router.push({name: "post-page", params: {id: this.post.id-1}})
                                            this.$router.push({name: "index"})
                                        }
                                    })
                                    .catch(error => {alert("ERRR"); this.error = error});
                            }
                        });
                } else {
                    fetch("http://localhost:8080/api/v1/post/update", {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            postId: this.post.id,
                            title: this.post.title,
                            category: this.post.category,
                            address: this.post.address,
                            description: this.post.description,
                            price: this.post.price,
                            photoUrl: this.post.url,
                            authorId: JSON.parse(localStorage.user).id
                        })
                    }).then(response => response.json())
                        .then(data => {
                            if (data["message"]) {
                                this.error = data["message"];
                            } else {
                                this.emitter.emit("onAddPost")
                                // this.$router.push({name: "post-page", params: {id: this.post.id-1}})
                                this.$router.push({name: "index"})
                            }
                        })
                        .catch(error => {alert("ERRR"); this.error = error});
                }

            }

        },
        hide() {

        },
        del() {
            fetch("http://localhost:8080/api/v1/post/delete/" + this.post.id, {
                method: 'DELETE',
            }).then(response => response.json())
                .then(data => {
                    alert(data)
                    if (data["message"]) {
                        alert("MASD");
                        this.error = data["message"];
                    } else {
                        alert(this.post.id);
                        // this.$router.push({name: "post-page", params: {id: this.post.id-1}})
                        this.$router.push({name: "index"})
                    }
                })
                .catch(error => {alert("ERRR"); this.error = error});
        }
    },
    computed: {
        cssVars() {
            var u = "";
            if (this.file) {
                u = URL.createObjectURL(this.file);
            } else {
                u = (this.post.url) ? this.post.url : this.url;
            }
            return {
                'content': 'url(' + u + ')'
            }
        },
        display() {
            if (!localStorage.user) {
                return 'hidden';

            }
            var user = JSON.parse(localStorage.user);
            if (user && this.post.author.id === user.id) {
                // do nothing
            } else {
                return 'hidden'
            }
        }
    },
    props: ['categories']
}
</script>

<template>
    <div>
        <div class="header" v-if="!post.id">
            Товар не найден
        </div>
        <div class="header" v-else>
            {{ post.title }}
        </div>
        <div class="post-holder">
            <div v-if="post.id" :class="{ 'flipped': isFlipped }" class="post">
                <div class="card front">
                    <div class="post-img">
                        <img :style="cssVars">

                        <div :class="display" @click.prevent="edit" class="post-editor">
                            <svg fill="#000000" width="25px" height="25px" viewBox="0 0 32 32" version="1.1"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M30.133 1.552c-1.090-1.044-2.291-1.573-3.574-1.573-2.006 0-3.47 1.296-3.87 1.693-0.564 0.558-19.786 19.788-19.786 19.788-0.126 0.126-0.217 0.284-0.264 0.456-0.433 1.602-2.605 8.71-2.627 8.782-0.112 0.364-0.012 0.761 0.256 1.029 0.193 0.192 0.45 0.295 0.713 0.295 0.104 0 0.208-0.016 0.31-0.049 0.073-0.024 7.41-2.395 8.618-2.756 0.159-0.048 0.305-0.134 0.423-0.251 0.763-0.754 18.691-18.483 19.881-19.712 1.231-1.268 1.843-2.59 1.819-3.925-0.025-1.319-0.664-2.589-1.901-3.776zM22.37 4.87c0.509 0.123 1.711 0.527 2.938 1.765 1.24 1.251 1.575 2.681 1.638 3.007-3.932 3.912-12.983 12.867-16.551 16.396-0.329-0.767-0.862-1.692-1.719-2.555-1.046-1.054-2.111-1.649-2.932-1.984 3.531-3.532 12.753-12.757 16.625-16.628zM4.387 23.186c0.55 0.146 1.691 0.57 2.854 1.742 0.896 0.904 1.319 1.9 1.509 2.508-1.39 0.447-4.434 1.497-6.367 2.121 0.573-1.886 1.541-4.822 2.004-6.371zM28.763 7.824c-0.041 0.042-0.109 0.11-0.19 0.192-0.316-0.814-0.87-1.86-1.831-2.828-0.981-0.989-1.976-1.572-2.773-1.917 0.068-0.067 0.12-0.12 0.141-0.14 0.114-0.113 1.153-1.106 2.447-1.106 0.745 0 1.477 0.34 2.175 1.010 0.828 0.795 1.256 1.579 1.27 2.331 0.014 0.768-0.404 1.595-1.24 2.458z"></path>
                            </svg>
                        </div>
                    </div>

                    <div class="post-text">
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Цена:
                            </div>
                            <div class="p-tex post-article-text">
                                {{ post.price }} ₽
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Категория:
                            </div>
                            <div @click.prevent="onCat" class="p-tex post-article-text clickable">
                                {{ post.category }}
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Описание:
                            </div>
                            <div class="p-tex post-article-text">
                                {{ post.description }}
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Адрес:
                            </div>
                            <div class="p-tex post-article-text">
                                {{ post.address }}
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Владелец:
                            </div>
                            <div @click.prevent="onAuthor" class="clickable p-tex post-article-text">
                                {{ post.author.name }} {{ post.author.surname }}
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Телефон
                            </div>
                            <div class="p-tex post-article-text">
                                {{ post.author.phone }}
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Опубликовано
                            </div>
                            <div class="p-tex post-article-text">
                                {{
                                    new Intl.DateTimeFormat("ru-RU",
                                        {
                                            year: "numeric",
                                            month: "long",
                                            day: "numeric",
                                            hour: "numeric",
                                            minute: "numeric",
                                        }).format(new Date(post.createdTime))
                                }}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card back">
                    <div class="post-img">
                        <img :style="cssVars"/>

                        <div :class="display" @click.prevent="edit" class="post-editor">
                            <svg fill="#000000" width="25px" height="25px" viewBox="0 0 32 32" version="1.1"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M30.133 1.552c-1.090-1.044-2.291-1.573-3.574-1.573-2.006 0-3.47 1.296-3.87 1.693-0.564 0.558-19.786 19.788-19.786 19.788-0.126 0.126-0.217 0.284-0.264 0.456-0.433 1.602-2.605 8.71-2.627 8.782-0.112 0.364-0.012 0.761 0.256 1.029 0.193 0.192 0.45 0.295 0.713 0.295 0.104 0 0.208-0.016 0.31-0.049 0.073-0.024 7.41-2.395 8.618-2.756 0.159-0.048 0.305-0.134 0.423-0.251 0.763-0.754 18.691-18.483 19.881-19.712 1.231-1.268 1.843-2.59 1.819-3.925-0.025-1.319-0.664-2.589-1.901-3.776zM22.37 4.87c0.509 0.123 1.711 0.527 2.938 1.765 1.24 1.251 1.575 2.681 1.638 3.007-3.932 3.912-12.983 12.867-16.551 16.396-0.329-0.767-0.862-1.692-1.719-2.555-1.046-1.054-2.111-1.649-2.932-1.984 3.531-3.532 12.753-12.757 16.625-16.628zM4.387 23.186c0.55 0.146 1.691 0.57 2.854 1.742 0.896 0.904 1.319 1.9 1.509 2.508-1.39 0.447-4.434 1.497-6.367 2.121 0.573-1.886 1.541-4.822 2.004-6.371zM28.763 7.824c-0.041 0.042-0.109 0.11-0.19 0.192-0.316-0.814-0.87-1.86-1.831-2.828-0.981-0.989-1.976-1.572-2.773-1.917 0.068-0.067 0.12-0.12 0.141-0.14 0.114-0.113 1.153-1.106 2.447-1.106 0.745 0 1.477 0.34 2.175 1.010 0.828 0.795 1.256 1.579 1.27 2.331 0.014 0.768-0.404 1.595-1.24 2.458z"></path>
                            </svg>
                        </div>
                    </div>

                    <div class="post-text">
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Название:
                            </div>
                            <div class="place-holder">
                                <input v-model="post.title"/>
                            </div>
                            <div class="field error">{{ titleError }}</div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Цена:
                            </div>
                            <div class="place-holder">
                                <input v-model.lazy="post.price" v-money="money"/>
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Категория:
                            </div>
                            <div class="place-holder">
                                <select v-model="post.category">
                                    <option v-for="c in categories" :value="c">{{ c }}</option>
                                </select>
                            </div>
                            <div class="field error">{{ catError }}</div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Описание:
                            </div>
                            <div class="place-holder">
                                <textarea type="text" v-model="post.description"/>
                            </div>
                            <div class="field error">{{ descriptionError }}</div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Адрес:
                            </div>
                            <div class="place-holder">
                                <input type="text" v-model="post.address"/>
                            </div>
                            <div class="field error">{{ addressError }}</div>
                        </div>
                        <div class="post-article">
                            <div class="p-tex post-article-name">
                                Фотография:
                            </div>
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
                        <div class="form error">{{ error }}</div>
                        <div class="post-article">
                            <div class="send">
                                <input class="b-btn" @click.prevent="save" type="submit" value="Сохранить">
                            </div>
                        </div>
                        <div class="post-article">
                            <div class="send">
                                <input class="b-btn" @click.prevent="del" type="submit" value="Удалить">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
textarea {
    overflow: hidden
}

.b-btn {
    width: 100%;
    border-radius: 48px;
}

</style>