<template>
    <header>
        <div class="header index-centre">
            <ul class="navigation">
                <li>
                    <router-link to="/index">
                        Главная
                    </router-link>
                </li>
                <li>
                    <router-link to="/p">
                        Хуйня
                    </router-link>
                </li>
                <li>
                    <router-link to="/index">
                        Не главная
                    </router-link>
                </li>
            </ul>
            <div class="authorization">
                <template v-if="!user">
                    <router-link to="/enter">
                        Войти
                    </router-link>
                    |
                    <router-link to="/register">
                        Зарегистрироваться
                    </router-link>
                </template>
                <template v-else>
                    <router-link to="/home">
                        {{ user.name }} {{ user.name }}
                    </router-link>
                    |
                    <a @click.prevent="logout">
                        Выйти
                    </a>
                </template>
            </div>
        </div>
    </header>
</template>


<script>

import {store} from "core-js/internals/reflect-metadata";

export default {
    props: ['user'],
    methods: {
        logout() {
            this.emitter.emit('onLogout');
        }
    }
}

</script>

<style scoped>

</style>