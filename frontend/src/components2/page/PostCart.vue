<link rel="stylesheet" href="../../../public/css2/general.css">
<script>
import {defineComponent} from 'vue'

export default {
    props: ['post', 'userId'],
    data: function () {
        return {
            url: "https://i.ibb.co/P55Cv8F/slon.jpg"
        }
    },
    methods: {
        onPost() {
            // this.emitter.emit('onShowPost', this.post.id)
            this.$router.push(
                {
                    name: 'post-page',
                    params: {
                        id: this.post.id
                    },
                });
        },
        hidde() {
            alert("hidde");
        },
        remove() {
            alert("remove");
        }
    },
    computed: {
        cssVars() {
            return {
                'content': 'url(' + ((this.post.url) ? this.post.url : this.url) + ')'
            }
        },

    }
}
</script>

<template>
    <div class="item-cart">
        <div @click.prevent="onPost" class="item-pic">
            <template v-if="userId">
                <div class="admin-button">
                    <button>?</button>
                    <button>x</button>
                </div>
            </template>
            <img :style="cssVars"/>
        </div>

        <!--        <div :class="display">-->
        <!--            <svg @click.prevent="hidde" class="m-button" fill="#000000" height="35px" width="35px" version="1.1" xmlns="http://www.w3.org/2000/svg"-->
        <!--                 viewBox="0 0 512 512" xmlns:xlink="http://www.w3.org/1999/xlink" enable-background="new 0 0 512 512">-->
        <!--                <g>-->
        <!--                    <path d="m494.8,241.4l-50.6-49.4c-50.1-48.9-116.9-75.8-188.2-75.8s-138.1,26.9-188.2,75.8l-50.6,49.4c-11.3,12.3-4.3,25.4 0,29.2l50.6,49.4c50.1,48.9 116.9,75.8 188.2,75.8s138.1-26.9 188.2-75.8l50.6-49.4c4-3.8 11.7-16.4 0-29.2zm-238.8,84.4c-38.5,0-69.8-31.3-69.8-69.8 0-38.5 31.3-69.8 69.8-69.8 38.5,0 69.8,31.3 69.8,69.8 0,38.5-31.3,69.8-69.8,69.8zm-195.3-69.8l35.7-34.8c27-26.4 59.8-45.2 95.7-55.4-28.2,20.1-46.6,53-46.6,90.1 0,37.1 18.4,70.1 46.6,90.1-35.9-10.2-68.7-29-95.7-55.3l-35.7-34.7zm355,34.8c-27,26.3-59.8,45.1-95.7,55.3 28.2-20.1 46.6-53 46.6-90.1 0-37.2-18.4-70.1-46.6-90.1 35.9,10.2 68.7,29 95.7,55.4l35.6,34.8-35.6,34.7z"/>-->
        <!--                </g>-->
        <!--            </svg>-->
        <!--            <svg @click.prevent="remove" class="m-button" fill="#000000" width="35px" height="35px" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">-->
        <!--                <path d="M0 14.545L1.455 16 8 9.455 14.545 16 16 14.545 9.455 8 16 1.455 14.545 0 8 6.545 1.455 0 0 1.455 6.545 8z"-->
        <!--                      fill-rule="evenodd"/>-->
        <!--            </svg>-->
        <!--        </div>-->
        <div @click.prevent="onPost" class="item-text">
            <div class="item-text-top">
                <div class="text-block item-title">
                    {{ post.title }}
                </div>
                <div class="text-block item-price">
                    {{ post.price }} ₽
                </div>
            </div>
            <div class="item-text-mid">
                <div class="item-description">
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
</template>

<style scoped>
</style>