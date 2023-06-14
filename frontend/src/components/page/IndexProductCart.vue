<script>
import {defineComponent} from 'vue'

export default {
    props: ['item']
}
</script>

<template>
    <div class="index-product-cart">
        <div class="index-product-cart-photo">
            <router-link to="#">
                <img src="../../../public/slon.jpg" alt="slon"/>
            </router-link>
        </div>
        <div class="index-prduct-info">
            <div class="product-title">
                <router-link to="#">{{ item.name }}</router-link>
            </div>
            <div class="product-price">
                {{ item.price }}
            </div>
            <div class="product-description">
                {{ item.description }}
            </div>
        </div>
    </div>

</template>

<style scoped>

</style>