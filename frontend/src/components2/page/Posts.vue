<script>
import {defineComponent} from 'vue'
import PostCart from "@/components2/page/PostCart.vue";

export default {
    components: {PostCart},
    props: ['posts']
}
</script>
<template>
    <div>
        <div class="header">
            Товары
        </div>
        <div class="items">
            <PostCart v-for="post in posts" :post="post"/>
        </div>
    </div>
</template>

<style scoped>

</style>