<template>
    <div class="index">
        <div class="avaliable-categories">
            <span class="title">
                Категории
            </span>
            <div class="cat-recomendation">
                <div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Слоны</router-link>
                </div>
                <div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Бегемоты</router-link>
                </div>
                <div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Динозвры</router-link>
                </div>
                <div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Ящеры</router-link>
                </div><div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Ящеры</router-link>
                </div><div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Ящеры</router-link>
                </div>
                <div class="product-cat text-box">
                    <router-link to="#" class="btn btn-white btn-animate">Ящеры</router-link>
                </div>
            </div>
        </div>
        <div class="product-content">
            <IndexProductCart :item="i" v-for="i in items"/>
        </div>
    </div>
</template>


<script>
import IndexProductCart from "@/components/page/IndexProductCart.vue";

export default {
    components: {IndexProductCart},
    props: ["items"]
}
</script>

<style scoped>

</style>