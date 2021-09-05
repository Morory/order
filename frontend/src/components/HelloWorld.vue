<template>
  <div>
    <h3>{{users}}</h3>
    <h1>Thank you!</h1>
  </div>
</template>

<script>
import http from "../http-common"

export default {
  name: 'HelloWorld',
  data() {
    return {
      users: []
    }
  },
  methods: {
    retrieveUsers() {
      http
      .get("/list")
      .then(response => {
        this.users = response.data;
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      })
    }
  },
  mounted() {
    this.retrieveUsers();
  }
}
</script>
