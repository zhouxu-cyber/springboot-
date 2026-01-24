<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 140%;">我的Bot</span>
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#bot-add-btn">
                            创建Bot
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="bot-add-btn" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="exampleModalLabel">创建Bot</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">名称</label>
                                            <input v-model="botadd.title" type="text" class="form-control"
                                                id="add-bot-title" placeholder="请输入Bot名称">
                                        </div>

                                        <div class="mb-3">
                                            <label for="add-bot-description" class="form-label">简介</label>
                                            <textarea v-model="botadd.description" class="form-control"
                                                id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                        </div>

                                        <div class="mb-3">
                                            <label for="add-bot-code" class="form-label">代码</label>
                                            <VAceEditor v-model:value="botadd.content" @init="editorInit" lang="c_cpp"
                                                theme="textmate" style="height: 300px" />
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <div class="error_message">
                                            {{ botadd.error_message }}
                                        </div>
                                        <button type="button" class="btn btn-primary" @click="add_bot">保存</button>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">取消</button>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 10px;"
                                            data-bs-toggle="modal"
                                            :data-bs-target="'#update-bot-modal-' + bot.id">修改</button>
                                        <button type="button" class="btn btn-danger"
                                            @click="remove_bot(bot)">删除</button>

                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-bot-modal-' + bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title fs-5">创建Bot</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>

                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label :for="'update-bot-title-' + bot.id"
                                                                class="form-label">名称</label>
                                                            <input v-model="bot.title" type="text" class="form-control"
                                                                :id="'update-bot-title-' + bot.id"
                                                                placeholder="请输入Bot名称">
                                                        </div>

                                                        <div class="mb-3">
                                                            <label :for="'update-bot-description-' + bot.id"
                                                                class="form-label">简介</label>
                                                            <textarea v-model="bot.description" class="form-control"
                                                                :id="'update-bot-description-' + bot.id" rows="3"
                                                                placeholder="请输入Bot简介"></textarea>
                                                        </div>

                                                        <div class="mb-3">
                                                            <label :for="'update-bot-code-' + bot.id"
                                                                class="form-label">代码</label>
                                                            <VAceEditor v-model:value="bot.content" @init="editorInit"
                                                                lang="c_cpp" theme="textmate" style="height: 300px" />
                                                        </div>
                                                    </div>

                                                    <div class="modal-footer">
                                                        <div class="error_message">
                                                            {{ bot.error_message }}
                                                        </div>
                                                        <button type="button" class="btn btn-primary"
                                                            @click="update_bot(bot)">保存修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">取消</button>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>

                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>

import { useStore } from "vuex";
import $ from "jquery";
import { ref, reactive } from "vue";
import { Modal } from "bootstrap/dist/js/bootstrap";
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/theme-github";
import "ace-builds/src-noconflict/ace";



export default {
    components: {
        VAceEditor
    },
    setup() {
        ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")
        const store = useStore();
        let bots = ref([]);

        const botadd = reactive({
            title: '',
            description: '',
            code: '',
            error_message: ''
        });

        const refresh_bots = () => {
            $.ajax({
                url: 'http://127.0.0.1:3000/user/bot/getlist/',
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + store.state.user.token
                },
                success: (resp) => {
                    bots.value = resp;
                }
            })
        }

        refresh_bots();

        const add_bot = () => {
            botadd.error_message = "";
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/add/",
                type: "POST",
                data: {
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content
                },
                headers: {
                    'Authorization': 'Bearer ' + store.state.user.token
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        // 关闭模态框
                        botadd.title = '';
                        botadd.description = '';
                        botadd.content = '';
                        Modal.getInstance('#bot-add-btn').hide();
                        document.activeElement?.blur();

                        refresh_bots();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            })
        }

        const remove_bot = (bot) => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/remove/",
                type: "POST",
                data: {
                    bot_id: bot.id
                },
                headers: {
                    'Authorization': 'Bearer ' + store.state.user.token
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        refresh_bots();
                    }
                }
            })
        }

        const update_bot = (bot) => {
            botadd.error_message = "";
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/update/",
                type: "POST",
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content
                },
                headers: {
                    'Authorization': 'Bearer ' + store.state.user.token
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        // 关闭模态框
                        Modal.getInstance('#update-bot-modal-' + bot.id).hide();
                        document.activeElement?.blur();

                        refresh_bots();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            })
        }

        return {
            bots,
            botadd,
            add_bot,
            update_bot,
            remove_bot
        }
    }
}
</script>

<style scoped>
.error_message {
    color: red;
    float: left;
    line-height: 38px;
}
</style>