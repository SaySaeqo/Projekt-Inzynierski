import { createStore } from 'vuex';

export default createStore({
  state: {
    username: '',
  },
  mutations: {
    setUsername(state, username) {
      state.username = username;
    },
    wrongLoggin(state) {
      alert('Wrong username or password');
    }
  },
});