<template>
  <div>
    <form @submit.prevent="submitForm(email, password)">
      <div>
        <label for="username">Username:</label>
        <input v-model="email" type="email" placeholder="Email" />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script lang="ts">
import firebaseApp from "@/initfirestore";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import { defineComponent } from "vue";
export default defineComponent({
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    submitForm(email: string, password: string) {
      const auth = getAuth(firebaseApp);
      signInWithEmailAndPassword(auth, email, password)
        .then((userCredential) => {
          // Signed in
          const user = userCredential.user;
          this.$router.push("/");
        })
        .catch((error) => {
          console.error(error);
          alert("Wrong username or password");
        });
    },
  },

});
</script>

<style lang="scss" scoped>
form {
  display: flex;
  flex-direction: column;
  width: 50%;
  margin: 0 auto;
}
div {
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;
}
label {
  margin-bottom: 0.5em;
}
input {
  padding: 0.5em;
  border: 1px solid black;
}
</style>
