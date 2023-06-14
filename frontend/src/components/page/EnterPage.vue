<script>
import {defineComponent} from 'vue'

export default {
    data: function () {
        return {
            login: "",
            password: "",
            error: ""
        }
    },
    methods: {
        enter() {
            if (!this.login.trim() || !this.password.trim()) {
                this.error = "Заполните все поля";
            } else {
                this.error = ""
            }
            if (this.error === "") {
                fetch("http://localhost:8080/api/v1/user/enter", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        login: this.login,
                        password: this.password
                    })
                }).then(response => response.json())
                    .then(data => {
                        if (data["user"]) {
                            alert(data["user"])
                            // this.$store.commit('login', JSON.stringify(data.user));
                            this.emitter.emit('onLogin', data.user)
                            this.$router.push('index');
                        } else if (data["message"]) {
                            this.error = data["message"];
                        }
                    })
                    .catch(error => this.error = error);
            }
        }
    }
}
</script>

<template>
    <div class="form enter">
        <div class="header">
            Войти
        </div>
        <form @submit.prevent="enter">
            <div class="data">
                <div class="field">
                    <div class="name">
                        Email
                    </div>
                    <div class="place-holder">
                        <input type="text" v-model="login"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        Пароль
                    </div>
                    <div class="place-holder">
                        <input type="password" v-model="password"/>
                    </div>
                </div>
            </div>
            <div class="field error">{{ error }}</div>
            <div class="send">
                <input type="submit" value="Войти">
            </div>
        </form>
    </div>
</template>

<style scoped>

</style>