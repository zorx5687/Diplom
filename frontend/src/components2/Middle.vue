<script>
import {defineComponent} from 'vue'

export default defineComponent({
    name: "Middle",
    props: ['clicked', 'user', 'currentUser', 'categories', 'posts', 'post'],
    data: function () {
        return {
            filterPost: this.posts,
            cat: [
                "Браслеты",
                "Вышивка",
                "Вязание",
                "Лепка",
                "Бисер",
                "Зеленые слоники",

            ]
        }
    },
    methods: {
        showCategory(name) {

            this.emitter.emit('onShowCat', name)
        }
    }
})
</script>

<template>
    <div>
        <div class="body">
            <aside class="b-sidebar">
                <div class="b-sidebar-content">
                    <form>
                        <div class="b-sidebar-block">
                            <div class="b-sidebar-block-item" v-for="c in categories">
                                <button class="b-sidebar-block-item-name"
                                        @click.prevent="showCategory(c)"
                                        :style="{
                                    'background-color': (c===clicked) ? 'black' : '#f5f5f5',
                                    'color': (c===clicked) ? 'white' : 'black',
                                }">
                                    {{ c }}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </aside>
            <div class="b-content">
                <section class="b-content-items">
                    <router-view :post="post" :posts="posts" :categories="categories" :currentUser="currentUser"
                                 :user="user"/>
                </section>
            </div>
        </div>
    </div>
</template>

<style scoped>
button {
    border: 0;
    font-size: 16px;
    font-family: 'ALS Hauss';
}

</style>