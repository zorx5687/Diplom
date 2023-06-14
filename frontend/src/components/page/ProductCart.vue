<script>
import {defineComponent} from 'vue'

export default {
    props: ['item']
}

</script>

<template>
    <div class="product-cart">
        <div class="cart-text product-cart-header">
            <span class="name">{{ item.name }} </span>
            <span class="price">Цена: {{item.price}}</span>
        </div>
        <div class="product-cart-photo">
            <img src="../../../public/slon.jpg"/>
        </div>
        <div class="cart-text product-cart-info">
            <div class="title">
                Описание
            </div>
            <div class="description">
                {{ item.description }}
            </div>
            <div class="title">
                Адресс
            </div>
            <div class="description">
                {{ item.address }}
            </div>
            <div class="title">
                Контакты
            </div>
            <div class="description">
                {{ item.phone }} {{item.author}}
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>