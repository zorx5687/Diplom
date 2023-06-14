<script>
import {defineComponent} from 'vue'
import {vMaska} from "maska";
import data from "@/data";
import axios from "axios";
import PostCart from "@/components2/page/PostCart.vue";

export default {
    components: {PostCart},
    directives: {
        maska: vMaska
    },
    name: "ProfilePage",
    // props: ['user'],
    data: function () {
        return {
            currentUser: null,
            userNotFound: false,
            email: "",
            name: "",
            surname: "",
            phone: "",
            error: "",
            errorEmail: "",
            errorName: "",
            errorSurname: "",
            errorPhone: "",
            user: "",
            posts: "",
            isSettings: false
        }
    },
    // beforeCreate() {
    //     const id = this.$route.params.id;
    //     if (!this.currentUser || this.currentUser.id !== id) {
    //         this.emitter.emit('onShowUser', id);
    //     }
    // },
    beforeMount() {
        let resuset = 'http://localhost:8080/api/v1/user/' + this.$route.params.id;
        axios.get(resuset)
            .then(response => {
                if (response.status === 200) {
                    this.currentUser = response.data.user;
                    this.posts = response.data.posts
                    if (!localStorage.user) {
                        this.user = {
                            id: "",
                        }
                    } else {
                        this.user = JSON.parse(localStorage.user);
                    }
                }
            }).catch(e => this.currentUser = {
            phone: "",
            surname: "",
            name: "",
            email: "",
            id: ""

        });
    },
    methods: {
        onName() {
            let validName = this.name.trim();
            if (!validName) {
                this.errorName = "Поле должно быть заполнено";
            } else if (!/^[а-яА-Я][а-я]+$/.test(this.name)) {
                this.errorName = "Имя может содержать только кириллицу без пробелов";
            } else if (validName.length < 2 || validName.length > 22) {
                this.errorName = "Имя должно содержать от 2 до 22 букв";
            } else {
                this.errorName = "";
            }
            if (this.errorName === "") {
                fetch("http://localhost:8080/api/v1/user/edit/name", {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: this.user.id,
                        name: this.name
                    })
                }).then(response => response.json())
                    .then(data => {
                        alert(JSON.stringify(data))
                        if (data) {
                            alert(data.name)
                            this.user.name = data.name;
                            this.currentUser.name = data.name;
                            localStorage.user = JSON.stringify(this.user);
                        } else {
                            this.errorName = data["message"]
                        }
                    })
                    .catch(error => this.errorName = error);
            }
        },
        onSurname() {

            let validSurname = this.surname.trim();
            if (!validSurname) {
                this.errorSurname = "Поле должно быть заполнено";
            } else if (!/^[а-яА-Я][а-я]+$/.test(this.surname)) {
                this.errorSurname = "Фамилия может содержать только кириллицу без пробелов";
            } else if (validSurname.length < 2 || validSurname.length > 22) {
                this.errorSurname = "Фамилия должно содержать от 2 до 22 букв";
            } else {
                this.errorSurname = "";
            }
            if (this.errorSurname === "") {
                fetch("http://localhost:8080/api/v1/user/edit/surname", {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: this.user.id,
                        surname: this.surname
                    })
                }).then(response => response.json())
                    .then(data => {
                        if (data) {
                            this.user.surname = data.surname;
                            this.currentUser.surname = data.surname;
                            localStorage.user = JSON.stringify(this.user);
                        } else {
                            this.errorSurname = data["message"]
                        }
                    })
                    .catch(error => this.errorSurname = error);
            }
        },
        onPhone() {
            if (!this.phone) {
                this.errorPhone = "Поле должно быть заполнено";
            } else if (!/^\+7 \([0-9]{3}\) [0-9]{3}-[0-9]{2}-[0-9]{2}/.test(this.phone)) {
                this.errorPhone = "Формат номера +7 (999) 999-99-99";
            } else {
                this.errorPhone = ""
            }

            if (this.errorPhone === "") {
                fetch("http://localhost:8080/api/v1/user/edit/phone", {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: this.user.id,
                        phone: this.phone
                    })
                }).then(response => response.json())
                    .then(data => {
                        if (data) {
                            this.user.phone = data.phone;
                            this.currentUser.phone = data.phone;
                            localStorage.user = JSON.stringify(this.user);
                        } else {
                            this.errorPhone = data["message"]
                        }
                    })
                    .catch(error => this.errorPhone = error);
            }
        },
        onEmail() {

            if (!this.email) {
                this.errorEmail = "Поле должно быть заполнено";
            } else if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)) {
                this.errorEmail = "Email неверен";
            } else {
                this.errorEmail = ""
            }

            if (this.errorEmail === "") {
                fetch("http://localhost:8080/api/v1/user/edit/email", {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: this.user.id,
                        email: this.email
                    })
                }).then(response => response.json())
                    .then(data => {
                        if (data) {
                            this.user.email = data.email;
                            this.currentUser.email = data.email;
                            localStorage.user = JSON.stringify(this.user);
                        } else {
                            this.errorEmail = data["message"]
                        }
                    })
                    .catch(error => this.errorEmail = error);
            }
        },
        onSettings() {
            var toggleFormButton = document.getElementById('settingsButton');
            var formContainer = document.getElementById('settingsForm');

            formContainer.classList.toggle('open');
            if (formContainer.classList.contains('open')) {
                formContainer.style.opacity = 1;
            } else {
                formContainer.style.opacity = 0;
            }

        }
    }
}
</script>

<style src="/public/css2/general.css"></style>
<template>
    <div class="user">
        <template v-if="currentUser.id">
            <div class="userHeader">
                <div class="user-left">
                    <div class="user-pic">
                        <svg class="profile" fill="#000000" height="64px" width="64px" version="1.1"
                             id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                             xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 504.331 504.331"
                             xml:space="preserve"><g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier"> <g transform="translate(0 -1)"> <g> <g> <circle
                                    cx="201.445" cy="183.608" r="8.393"></circle>
                                <circle cx="302.166" cy="183.608" r="8.393"></circle>
                                <path d="M501.219,195.113c-9.329-40.313-28.543-61.994-55.367-66.337c-17.546-2.841-36.581,1.929-56.154,11.904 c-5.504-11.782-14.474-24.072-27.866-35.121C336.675,84.804,301.01,73.34,253.442,74.58 c-46.788-1.232-82.827,10.054-108.914,30.499c-14.18,11.114-23.901,23.558-30.046,35.525 c-19.668-9.996-38.775-14.728-56.359-11.771c-26.649,4.482-45.727,26.151-55.009,66.279 c-6.745,29.162-2.505,59.228,12.456,83.707c21.328,34.905,55.38,46.509,101.135,23.461c15.95,24.623,46.837,38.73,86.331,45.323 l5.648,59.311c1.143,11.896,13.85,18.939,24.535,13.605c0.09-0.045,6.557,6.34,6.557,6.34c6.644,6.644,17.414,6.644,24.057,0 l6.303-6.303c10.941,5.299,23.663-1.75,24.79-13.646l5.642-59.24c39.43-6.588,70.329-20.806,86.424-45.688 c46.068,23.475,80.321,11.931,101.762-23.164C503.718,254.337,507.963,224.269,501.219,195.113z M474.431,270.065 c-17.97,29.415-44.799,37.322-86.495,13.335c-4.217-2.426-9.606-0.77-11.734,3.605c-10.969,22.557-37.803,36.555-73.981,43.329 l3.698-38.831c23.016-8.402,43.834-22.052,61.036-39.832c3.223-3.332,3.135-8.645-0.196-11.868 c-3.332-3.223-8.645-3.135-11.868,0.196c-13.546,14.001-29.56,25.137-47.184,32.74l2.817-29.58 c0.126-1.323-0.071-2.599-0.512-3.763c-0.159-0.42-0.349-0.826-0.572-1.212c-0.357-0.62-0.788-1.194-1.289-1.703 c-0.019-0.019-0.035-0.04-0.054-0.059c-0.531-0.53-1.137-0.985-1.797-1.36c-0.05-0.028-0.096-0.061-0.146-0.088 c-0.599-0.323-1.248-0.567-1.928-0.738c-0.41-0.103-0.828-0.187-1.261-0.228c-0.288-0.027-0.575-0.04-0.858-0.038 c-1.004,0.006-1.969,0.201-2.869,0.537c-0.371,0.139-0.735,0.294-1.08,0.483c-0.394,0.215-0.764,0.467-1.118,0.741 c-0.636,0.492-1.2,1.073-1.674,1.729c-0.268,0.371-0.506,0.765-0.711,1.18c-0.166,0.335-0.312,0.683-0.433,1.043 c-0.069,0.204-0.127,0.412-0.181,0.623c-0.103,0.41-0.187,0.828-0.228,1.261l-4.094,42.988c-0.004,0.042-0.009,0.084-0.012,0.127 l-11.489,120.641c-6.861-3.099-14.772-1.813-19.948,3.363l-6.303,6.303c-0.088,0.088-6.621-6.303-6.621-6.303 c-5.176-5.176-13.087-6.462-19.634-3.18c-0.131,0.065-15.909-163.939-15.909-163.939c-0.02-0.211-0.056-0.417-0.091-0.622 c-0.013-0.075-0.019-0.153-0.033-0.228c-0.088-0.443-0.219-0.867-0.373-1.28c-0.136-0.365-0.287-0.722-0.469-1.06 c-0.054-0.1-0.118-0.194-0.177-0.292c-0.219-0.369-0.458-0.725-0.729-1.054c-0.998-1.212-2.328-2.127-3.833-2.629 c-0.045-0.015-0.087-0.035-0.132-0.049c-0.721-0.227-1.483-0.352-2.266-0.374c-0.064-0.002-0.126-0.01-0.191-0.011 c-0.284-0.002-0.57,0.011-0.859,0.038c-0.169,0.016-0.332,0.047-0.498,0.073c-0.117,0.018-0.237,0.029-0.352,0.052 c-0.369,0.073-0.724,0.179-1.073,0.298c-0.439,0.15-0.866,0.327-1.267,0.544c-0.1,0.054-0.194,0.118-0.292,0.177 c-0.603,0.358-1.161,0.781-1.651,1.271c-0.057,0.057-0.106,0.119-0.161,0.177c-0.13,0.137-0.261,0.273-0.381,0.419 c-0.267,0.324-0.502,0.672-0.719,1.033c-0.061,0.101-0.127,0.198-0.183,0.301c-0.017,0.031-0.029,0.064-0.046,0.095 c-0.11,0.207-0.215,0.417-0.307,0.634c-0.026,0.06-0.044,0.123-0.068,0.184c-0.177,0.443-0.323,0.9-0.422,1.374 c-0.098,0.467-0.148,0.948-0.165,1.437c-0.003,0.076-0.012,0.15-0.013,0.225c-0.002,0.284,0.011,0.57,0.038,0.859l0.069,0.722 l2.747,28.853c-17.622-7.604-33.644-18.741-47.183-32.73c-3.224-3.331-8.538-3.418-11.869-0.194 c-3.331,3.224-3.417,8.538-0.194,11.869c17.194,17.764,38.024,31.418,61.032,39.821l3.691,38.768 c-36.258-6.774-63.093-20.67-73.857-42.92c-2.115-4.373-7.49-6.042-11.711-3.638c-41.371,23.57-68.044,15.612-85.921-13.644 c-12.601-20.617-16.206-46.178-10.425-71.17c7.871-34.026,22.17-50.268,41.438-53.508c15.336-2.579,33.874,2.977,53.34,14.179 c4.471,2.573,10.184,0.542,12.027-4.277c4.517-11.814,13.563-25.205,28.61-36.998c22.936-17.976,55.146-28.063,98.558-26.931 c44.075-1.138,75.775,9.051,97.708,27.146c14.278,11.78,22.483,25.074,26.277,36.678c1.659,5.074,7.57,7.331,12.188,4.653 c19.51-11.312,38.131-16.989,53.555-14.492c19.4,3.141,33.786,19.374,41.695,53.55 C490.643,223.882,487.034,249.445,474.431,270.065z"></path>
                                <path d="M95.178,169.98c0.646-4.162-1.893-8.159-5.935-9.342c-4.95-1.449-9.546-2.21-13.698-2.21 c-0.141-0.001-0.141-0.001-0.386-0.004c-3.792-0.047-6.6,0.368-10.17,2.18c-4.582,2.326-8.573,6.477-12.001,12.635 c-3.399,6.105-6.317,14.303-8.771,24.932c-4.963,21.521-1.655,42.926,9.427,60.169c8.646,13.454,16.359,17.595,26.047,17.595 c3.018,0,6.384-0.427,10.114-1.341c4.223-1.035,6.957-5.124,6.299-9.422C90.914,231.258,90.485,200.22,95.178,169.98z M67.766,249.265c-8.571-13.336-11.162-30.102-7.192-47.319c2.124-9.202,4.529-15.957,7.081-20.541 c3.12-5.605,4.353-6.231,7.294-6.194c0.267,0.003,0.267,0.003,0.595,0.005c0.605,0,1.249,0.032,1.931,0.095 c-3.414,26.896-3.06,54.417,0.806,83.771C75.044,258.728,72.622,256.822,67.766,249.265z"></path>
                                <path d="M450.624,173.248c-3.428-6.158-7.419-10.309-12.001-12.635c-3.57-1.812-6.378-2.227-10.17-2.18 c-0.245,0.003-0.245,0.003-0.386,0.004c-4.152,0-8.749,0.761-13.698,2.21c-4.042,1.184-6.581,5.18-5.935,9.342 c4.693,30.239,4.263,61.278-0.926,95.193c-0.658,4.298,2.076,8.387,6.299,9.422c3.73,0.914,7.096,1.341,10.114,1.341 c9.688,0,17.4-4.141,26.047-17.596c11.082-17.242,14.39-38.647,9.427-60.167C456.941,187.551,454.022,179.353,450.624,173.248z M435.846,249.273c-4.857,7.557-7.278,9.463-10.515,9.816c3.866-29.354,4.22-56.875,0.806-83.771 c0.681-0.063,1.326-0.095,1.931-0.095c0.329-0.002,0.329-0.002,0.595-0.005c2.941-0.037,4.173,0.589,7.294,6.194 c2.552,4.584,4.957,11.34,7.082,20.543C447.008,219.171,444.417,235.937,435.846,249.273z"></path> </g> </g> </g> </g></svg>
                    </div>
                    <div class="user-info">
                        <div class="user-name">
                            {{ currentUser.name }} {{ currentUser.surname }}
                        </div>
                        <div class="user-mail" v-if="user.id && currentUser.id === user.id">
                            {{ currentUser.email }}
                        </div>
                    </div>
                    <button v-if="currentUser.id === user.id" @click.prevent="onSettings" id="settingsButton">
                        <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="48" height="48"
                             viewBox="0 0 24 24">
                            <path d="M 9.6660156 2 L 9.1757812 4.5234375 C 8.3516137 4.8342536 7.5947862 5.2699307 6.9316406 5.8144531 L 4.5078125 4.9785156 L 2.171875 9.0214844 L 4.1132812 10.708984 C 4.0386488 11.16721 4 11.591845 4 12 C 4 12.408768 4.0398071 12.832626 4.1132812 13.291016 L 4.1132812 13.292969 L 2.171875 14.980469 L 4.5078125 19.021484 L 6.9296875 18.1875 C 7.5928951 18.732319 8.3514346 19.165567 9.1757812 19.476562 L 9.6660156 22 L 14.333984 22 L 14.824219 19.476562 C 15.648925 19.165543 16.404903 18.73057 17.068359 18.185547 L 19.492188 19.021484 L 21.826172 14.980469 L 19.886719 13.291016 C 19.961351 12.83279 20 12.408155 20 12 C 20 11.592457 19.96113 11.168374 19.886719 10.710938 L 19.886719 10.708984 L 21.828125 9.0195312 L 19.492188 4.9785156 L 17.070312 5.8125 C 16.407106 5.2676813 15.648565 4.8344327 14.824219 4.5234375 L 14.333984 2 L 9.6660156 2 z M 11.314453 4 L 12.685547 4 L 13.074219 6 L 14.117188 6.3945312 C 14.745852 6.63147 15.310672 6.9567546 15.800781 7.359375 L 16.664062 8.0664062 L 18.585938 7.40625 L 19.271484 8.5917969 L 17.736328 9.9277344 L 17.912109 11.027344 L 17.912109 11.029297 C 17.973258 11.404235 18 11.718768 18 12 C 18 12.281232 17.973259 12.595718 17.912109 12.970703 L 17.734375 14.070312 L 19.269531 15.40625 L 18.583984 16.59375 L 16.664062 15.931641 L 15.798828 16.640625 C 15.308719 17.043245 14.745852 17.36853 14.117188 17.605469 L 14.115234 17.605469 L 13.072266 18 L 12.683594 20 L 11.314453 20 L 10.925781 18 L 9.8828125 17.605469 C 9.2541467 17.36853 8.6893282 17.043245 8.1992188 16.640625 L 7.3359375 15.933594 L 5.4140625 16.59375 L 4.7285156 15.408203 L 6.265625 14.070312 L 6.0878906 12.974609 L 6.0878906 12.972656 C 6.0276183 12.596088 6 12.280673 6 12 C 6 11.718768 6.026742 11.404282 6.0878906 11.029297 L 6.265625 9.9296875 L 4.7285156 8.59375 L 5.4140625 7.40625 L 7.3359375 8.0683594 L 8.1992188 7.359375 C 8.6893282 6.9567546 9.2541467 6.6314701 9.8828125 6.3945312 L 10.925781 6 L 11.314453 4 z M 12 8 C 9.8034768 8 8 9.8034768 8 12 C 8 14.196523 9.8034768 16 12 16 C 14.196523 16 16 14.196523 16 12 C 16 9.8034768 14.196523 8 12 8 z M 12 10 C 13.111477 10 14 10.888523 14 12 C 14 13.111477 13.111477 14 12 14 C 10.888523 14 10 13.111477 10 12 C 10 10.888523 10.888523 10 12 10 z"></path>
                        </svg>
                    </button>
                </div>
            </div>
            <template v-if="currentUser.id === user.id">
                <div class="enter" id="settingsForm">
                    <div class="form">
                        <div class="header">
                            Редактировать профиль
                        </div>
                        <form @submit.prevent="onEmail">
                            <div class="data">
                                <div class="field">
                                    <div class="name">
                                        Email
                                    </div>
                                    <div class="place-holder">
                                        <input type="text" name="name" v-model="email"/>
                                    </div>
                                </div>
                                <div class="field error">{{ errorEmail }}</div>
                                <div class="send">
                                    <input class="b-btn" type="submit" value="Изменить">
                                </div>
                            </div>
                        </form>
                        <form @submit.prevent="onPhone">
                            <div class="data">
                                <div class="field">
                                    <div class="name">
                                        Телефон
                                    </div>
                                    <div class="place-holder">
                                        <input type="text" v-maska data-maska="+7 (###) ###-##-##"
                                               v-model="phone"/>
                                    </div>
                                </div>
                                <div class="field error">{{ errorPhone }}</div>
                                <div class="send">
                                    <input class="b-btn" type="submit" value="Изменить">
                                </div>
                            </div>
                        </form>
                        <form @submit.prevent="onName">
                            <div class="data">
                                <div class="field">
                                    <div class="name">
                                        Имя
                                    </div>
                                    <div class="place-holder">
                                        <input type="text" name="name" v-model="name"/>
                                    </div>
                                </div>
                                <div class="field error">{{ errorName }}</div>
                                <div class="send">
                                    <input class="b-btn" type="submit" value="Изменить">
                                </div>
                            </div>
                        </form>
                        <form @submit.prevent="onSurname">
                            <div class="data">
                                <div class="field">
                                    <div class="name">
                                        Фамилия
                                    </div>
                                    <div class="place-holder">
                                        <input type="text" name="name" v-model="surname"/>
                                    </div>
                                </div>
                                <div class="field error">{{ errorSurname }}</div>

                                <div class="send">
                                    <input class="b-btn" type="submit" value="Изменить">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </template>
            <div class="items">
                <PostCart v-for="post in posts" :post="post"/>
            </div>
        </template>
        <template v-else>
            <div class="header">
                Пользователь не найден
            </div>
        </template>
    </div>
</template>

<style scoped>
button {
    border: none;
    background-color: white;
    display: flex;
}

button:hover {
    cursor: pointer;
}

#settingsForm {
    padding-top: 1rem;
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s;
}

#settingsForm .header {
    margin-top: 1rem;
}

#settingsForm.open {
    max-height: 1500px;
    opacity: 1;

}
</style>