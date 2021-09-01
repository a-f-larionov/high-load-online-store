/* Main application script */
Vue.config.productionTip = false;

let bus = new Vue();

bus.EVENT_UPDATE_CURRENT_USER = 'update-current-user';
bus.EVENT_USER_LOGIN = 'user-login';
bus.EVENT_USER_LOGOUT = 'user-logout';

Vue.component("good-form", {

    template: "#templateGoodForm",

    data: function () {
        return {
            showIt: false,

            showAddButton: false,
            showUpdateButton: true,
            showDeleteButton: false,

            id: null,
            name: "",
            description: "",
            price: 0,
            quantity: 0
        };
    },

    created: function () {

        let self = this;

        self.showIt = app.currentUserIsAdmin;
        self.showAddButton = app.currentUserIsAdmin;


        if (app.editFormItem) {

            self.fillFields(app.editFormItem);
        }

        bus.$on(bus.EVENT_USER_LOGIN, function () {
            self.showIt = app.currentUserIsAdmin;
            self.showAddButton = app.currentUserIsAdmin;
        });

        bus.$on(bus.EVENT_USER_LOGOUT, function () {
            self.showIt = app.currentUserIsAdmin;
            self.showAddButton = false;
            self.showEditButton = false;
            self.showDeleteButton = false;
        });
    },
    mounted: function () {

    },
    methods: {
        fillFields: function (item) {

            this.id = item.id;
            this.name = item.name;
            this.description = item.description;
            this.price = item.price;
            this.quantity = item.quantity;

            this.showUpdateButton = true;
            this.showDeleteButton = true;
        },
        onAddButtonClick: function () {
            axios.post("/goods/add", {
                id: null,
                name: this.name,
                description: this.description,
                price: this.price,
                quantity: this.quantity,
            }).then(function (answer) {

                app.showBlock(app.blocks.GOODS_LIST);
            });
        },
        onUpdateButtonClick: function () {
            axios.post("/goods/update", {
                id: this.id,
                name: this.name,
                description: this.description,
                price: this.price,
                quantity: this.quantity,
            }).then(function (answer) {

                app.showBlock(app.blocks.GOODS_LIST);
            });
        },
        onDeleteButtonClick: function () {
            axios.post("/goods/delete", {
                id: this.id
            }).then(function (answer) {

                app.showBlock(app.blocks.GOODS_LIST);
            });
        }
    }
});

Vue.component("goods-list", {

    template: "#templateGoodsList",

    data: function () {
        return {
            items: []
        };
    },

    created: function () {
    },

    mounted: function () {
        let self = this;

        axios.post("/goods/get-list")
            .then(function (answer) {

                self.items = answer.data;
            });
    },
    methods: {}
});

Vue.component("goods-item", {

    template: "#templateGoodItem",

    props: ['item'],

    data: function () {
        return {
            showEditButton: false,
            showPurchaseButton: false,
            purchaseQuantity: 1,
        };
    },

    created: function () {

        let self = this;

        this.showEditButton = app.currentUserIsAdmin;
        this.showPurchaseButton = app.currentUser && !app.currentUserIsAdmin;

        bus.$on(bus.EVENT_USER_LOGIN, function () {
            self.showEditButton = app.currentUserIsAdmin;
            self.showPurchaseButton = app.currentUser && !app.currentUserIsAdmin;
        });

        bus.$on(bus.EVENT_USER_LOGOUT, function () {
            self.showEditButton = app.currentUserIsAdmin;
            self.showPurchaseButton = app.currentUser && !app.currentUserIsAdmin;
        });
    },

    mounted: function () {
    },

    methods: {
        onEditButtonClick: function (item) {

            app.showBlock(app.blocks.GOOD_FORM);

            app.editFormItem = item;
        },
        onPurchaseButtonClick: function (item) {

            axios.post("/purchase/makeOne", {
                goodId: item.id,
                quantity: this.purchaseQuantity,
            });
        }
    }
})

Vue.component("buttons-panel", {

    template: "#templateButtonsPanel",

    data: function () {
        return {showIt: false};
    },

    created: function () {
        let self = this;

        bus.$on(bus.EVENT_USER_LOGIN, function () {
            self.showIt = true
        });
        bus.$on(bus.EVENT_USER_LOGOUT, function () {
            self.showIt = false
        });
    },

    mounted: function () {
        bus.$emit(bus.EVENT_UPDATE_CURRENT_USER);
    },

    methods: {

        onLoginClick: function () {
            app.showBlock(app.blocks.LOGIN_FORM);
        },

        onLogoutClick: function () {
            axios.post("/logout")
                .then(function (answer) {
                    bus.$emit(bus.EVENT_UPDATE_CURRENT_USER);
                });
        },

        onRegistrationClick: function () {
            app.showBlock(app.blocks.REGISTER_FORM);
        }
    }
});

Vue.component("buttons-admin-panel", {

    template: "#templateButtonsAdminPanel",

    data: function () {
        return {showIt: false};
    },

    created: function () {
        let self = this;

        bus.$on(bus.EVENT_USER_LOGIN, function () {
            self.showIt = app.currentUserIsAdmin;
        });
        bus.$on(bus.EVENT_USER_LOGOUT, function () {
            self.showIt = app.currentUserIsAdmin;
        });
    },

    mounted: function () {
    },

    methods: {
        onClickGoodsIcon: function () {
            app.showBlock(app.blocks.GOODS_LIST);
        }
    }
});

Vue.component("login-form", {

    template: "#templateLoginForm",

    data: function () {
        return {
            password: "",
            username: ""
        };
    },

    methods: {
        loginDo: function () {

            var params = new URLSearchParams();

            params.append('username', this.username);
            params.append('password', this.password);

            var config = {
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                }
            };

            axios.post("/authorize", params, config)
                .then(function (answer) {
                    bus.$emit(bus.EVENT_UPDATE_CURRENT_USER);
                    app.showBlock(app.blocks.GOODS_LIST);
                });

        }
    }
});

Vue.component("register-form", {

    template: "#templateRegisterForm",

    data: function () {
        return {
            username: "",
            password: ""
        }
    },

    created: function () {

    },

    methods: {
        registerDo: function () {

            axios.post("/register-user", {
                username: this.username,
                password: this.password
            }).then(function (answer) {
                //@todo bootstrap notification
                app.showBlock(app.blocks.NONE);
                alert(answer.data);
            });
        }
    }
});

let app = new Vue({

    el: "#vueapp",

    created: function () {

        bus.$on(bus.EVENT_UPDATE_CURRENT_USER, this.updateCurrentUser);
    },

    mounted: function () {

        this.currentBlock = this.blocks.GOODS_LIST;
    },

    data: {
        currentBlock: null,
        blocks: {
            NONE: 0,
            LOGIN_FORM: 1,
            REGISTER_FORM: 2,
            GOODS_LIST: 3,
            GOOD_FORM: 4,
        },
        currentUser: null,
        currentUserIsAdmin: false,
        editFormItem: null
    },

    methods: {

        showBlock: function (blockId) {
            this.currentBlock = blockId;
        },

        isShowedBlock: function (blockId) {
            return this.currentBlock === blockId;
        },

        updateCurrentUser: function () {
            let self = this;

            axios.post("/get-current-user")
                .then(function (answer) {
                    if (!answer.data || !answer.data.id) {
                        self.currentUser = null;
                        return;
                    }

                    self.currentUser = answer.data;

                    if (self.currentUser.id) {

                        axios.post('/is-current-user-admin')
                            .then(function (answer) {
                                self.currentUserIsAdmin = answer.data === true;
                                bus.$emit(bus.EVENT_USER_LOGIN);
                            });

                        //alert("Авторизация успешна:" + self.currentUser.username);
                        app.showBlock(app.blocks.GOODS_LIST);
                    } else {
                        bus.$emit(bus.EVENT_USER_LOGOUT);
                        //alert("Пользователь вышел из системы.");
                        app.showBlock(app.blocks.GOODS_LIST);
                    }
                });
        }
    }
});