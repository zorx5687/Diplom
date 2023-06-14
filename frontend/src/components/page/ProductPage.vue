<script>
import ProductCart from "@/components/page/ProductCart.vue";
import SideBar from "@/components/page/SideBar.vue";
import data from "@/data";

export default {
    components: {SideBar, ProductCart},
    props: ['items', 'item'],
    computed: {
        three: function () {
            return Object.values(this.items).slice(0, 3);
        }
    }
}

</script>

<template>
    <div class="product-page">
        <div class="main-product">
            <ProductCart :item="item"/>
        </div>
        <div class="recent-products">
            <div class="title">
                Недавно добавленные
            </div>
            <SideBar :items="three"/>
        </div>
    </div>

</template>

<style scoped>

</style>