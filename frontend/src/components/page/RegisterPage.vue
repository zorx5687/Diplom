<script>
import {computed, defineComponent} from 'vue'
import {vMaska} from "maska"
import data from "@/data";
import {useStore} from "vuex";
import {store} from "core-js/internals/reflect-metadata";


export default {
    directives: {
        maska: vMaska
    },
    // setup() {
    //     const store = useStore()
    //
    //     return {
    //         user: computed(() => store.state.user),
    //         login: (newUser) => store.commit('login', newUser),
    //
    //     }
    // },
    data: function () {
        return {
            name: "",
            surname: "",
            email: "",
            mobilePhone: "",
            password: "",
            repeatPassword: "",
            nameError: "",
            surnameError: "",
            emailError: "",
            phoneError: "",
            passwordError: "",
            secondPasswordError: "",
            error: "",
            result: ""
        }
    },

    methods: {
        onRegister: function () {
            let validName = this.name.trim();
            let validSurname = this.surname.trim();
            alert(validName);
            if (!validName) {
                this.nameError = "Поле должно быть заполнено";
            } else if (!/^[а-яА-Я][а-я]+$/.test(this.name)) {
                this.nameError = "Имя может содержать только кириллицу без пробелов";
            } else if (validName.length < 2 || validName.length > 22) {
                this.nameError = "Имя должно содержать от 2 до 22 букв";
            } else {
                this.nameError = "";
            }

            if (!validSurname) {
                this.surnameError = "Поле должно быть заполнено";
            } else if (!/^[а-яА-Я][а-я]+$/.test(this.surname)) {
                this.surnameError = "Фамилия может содержать только кириллицу без пробелов";
            } else if (validSurname.length < 2 || validSurname.length > 22) {
                this.surnameError = "Фамилия должно содержать от 2 до 22 букв";
            } else {
                this.surnameError = "";
            }

            if (!this.mobilePhone) {
                this.phoneError = "Поле должно быть заполнено";
            } else if (!/^\+7 \([0-9]{3}\) [0-9]{3}-[0-9]{2}-[0-9]{2}/.test(this.mobilePhone)) {
                this.phoneError = "Формат номера +7 (999) 999-99-99";
            } else {
                this.phoneError = ""
            }

            if (!this.email) {
                this.emailError = "Поле должно быть заполнено";
            } else if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)) {
                this.emailError = "Email неверен";
            } else {
                this.emailError = ""
            }

            if (!this.password) {
                this.passwordError = "Поле должно быть заполнено";
                // this.password = "";
                // this.repeatPassword = "";
            } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(this.password)) {
                this.passwordError = "Пароль должен содержать минимум 8 символоов, минимум 1 букву и 1 цифру";
                // this.password = "";
            } else {
                this.passwordError = ""
            }

            if (!this.repeatPassword) {
                this.secondPasswordError = "Повторите пароль";
                // this.password = "";
                // this.repeatPassword = "";
            } else if (this.repeatPassword !== this.password && this.passwordError === "") {
                this.secondPasswordError = "Пароли не совпадают"
                // this.password = "";
                // this.repeatPassword = "";
            } else {
                this.secondPasswordError = ""
            }

            if (this.nameError === "" &&
                this.surnameError === "" &&
                this.emailError === "" &&
                this.phoneError === "" &&
                this.passwordError === "" &&
                this.secondPasswordError === "") {
                this.postRegister();
            }

        },
        postRegister() {
            fetch("http://localhost:8080/api/v1/user/register", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({

                    name: this.name,
                    surname: this.surname,
                    phone: this.mobilePhone,
                    email: this.email,
                    password: this.password

                })
            }).then(response => response.json())
                .then(data => {
                    if (data["user"]) {
                        alert(data["user"])
                        this.emitter.emit('onLogin', data.user)
                        this.$router.push('index');
                    } else if (data["message"]) {
                        this.error = data["message"];
                    }
                })
                .catch(error => this.error = error);
        }
    },
    beforeCreate() {
        this.emitter.on("onRegisterValidationError", (error) => this.error = error);
    }
}

</script>

<template>
    <div class="form enter">
        <div class="header">
            Зарегистриоваться
        </div>
        <form @submit.prevent="onRegister">
            <div class="data">
                <div class="field">
                    <div class="name">
                        Имя
                    </div>
                    <div class="place-holder">
                        <input type="text" name="name" v-model="name"/>
                    </div>
                </div>
                <div class="field error">{{ nameError }}</div>
                <div class="field">
                    <div class="name">
                        Фамилия
                    </div>
                    <div class="place-holder">
                        <input type="text" v-model="surname"/>
                    </div>
                </div>
                <div class="field error">{{ surnameError }}</div>
                <div class="field">
                    <div class="name">
                        Email
                    </div>
                    <div class="place-holder">
                        <input type="text" v-model="email"/>
                    </div>
                </div>
                <div class="field error">{{ emailError }}</div>
                <div class="field">
                    <div class="name">
                        Мобильный телефон
                    </div>
                    <div class="place-holder">
                        <input type="text" v-maska data-maska="+7 (###) ###-##-##" v-model="mobilePhone"/>
                    </div>
                </div>
                <div class="field error">{{ phoneError }}</div>
                <div class="field">
                    <div class="name">
                        Пароль
                    </div>
                    <div class="place-holder">
                        <input type="password" v-model="password"/>
                    </div>
                </div>
                <div class="field error">{{ passwordError }}</div>
                <div class="field">
                    <div class="name">
                        Повторите пароль
                    </div>
                    <div class="place-holder">
                        <input type="password" v-model="repeatPassword"/>
                    </div>
                </div>
                <div class="field error">{{ secondPasswordError }}</div>
            </div>
            <div class="field error">{{ error }}</div>
            <div class="send">
                <input type="submit" value="Регистрация">
            </div>
        </form>
    </div>
</template>

<style scoped>

</style>