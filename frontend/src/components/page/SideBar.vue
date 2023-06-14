<template>
    <div class="sidebar">
        <IndexProductCart :item="i" v-for="i in items"/>
    </div>
</template>


<script>
import ProductCart from "@/components/page/ProductCart.vue";
import IndexProductCart from "@/components/page/IndexProductCart.vue";
import data from "@/data";

export default {
    components: {IndexProductCart, ProductCart},
    props:['items']
}
</script>

<style scoped>

</style>