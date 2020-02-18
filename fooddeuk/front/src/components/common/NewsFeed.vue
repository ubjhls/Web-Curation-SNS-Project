<template>
<v-app>
    <div class="wrapC">
        <div class="wrapper" style="position:relative">
            <div class="profile-card js-profile-card">
                <div class="profile-card__cnt js-profile-cnt">
                    <img src="../../assets/images/profile_default.png" style="margin-bottom:10px;">
                        <div class="profile-card__name">{{nickname}}</div>
                        <div class="profile-card__txt">{{intro}}</div>

                        <div class="profile-card-ctr" v-if="isfollow==0">

                            <button class="profile-card__button button--orange" @click="followgo()">Follow</button>
                        </div>
                        <div class="profile-card-ctr" v-if="isfollow==1">

                            <button class="profile-card__button button--gray" @click="unfollowgo()">UnFollow</button>
                        </div>

                        <div class="profile-card-inf">
                            <div class="profile-card-inf__item">
                                <div class="profile-card-inf__title">{{follower}}</div>
                                <div class="profile-card-inf__txt">Followers</div>
                            </div>

                        <div class="profile-card-inf__item">
                            <div class="profile-card-inf__title">{{following}}</div>
                            <div class="profile-card-inf__txt">Following</div>
                        </div>
                    </div> 
                </div>
            </div>

            <hr>
            <div v-if="auth==1 && isfollow==0" style="margin-top:20px; text-align:center">
                비공개 계정입니다.
            </div>
            <div v-if="auth==0 || (auth==1 && isfollow==1)">
                <div v-if="!post" style="margin-top:20px; text-align:center"> 게시물이 없습니다.</div>
                  <div v-for="(item,index) in post" v-bind:key="item.num">  
                    <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="margin-bottom:100px;"
                    >
                        <div style="padding:1px; float:right; height:30px;" class="mynum" v-if="item.author == mynum ">
                            <dropdown>
                            <input id="toggle2" type="checkbox">
                            <label for="toggle2" class="animate" style="padding-left:40px"><i class="fas fa-ellipsis-v"></i></label>
                            <ul class="animate">
                                <li class="animate" style="line-height:5px"><button style="float:right;" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image)">수정</button></li>
                                <li class="animate" style="line-height:5px"><button style="float:right;" @click="removeFeed(item.num)">삭제</button></li>
                                
                            </ul>
                            </dropdown>
                        <!-- <button style="float:right" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image)">수정</button>
                        <button style="float:right" @click="removeFeed(item.num)">삭제</button> -->
                        </div>
                    <v-list-item>
                        <v-list-item-avatar><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-left:10px" class="headline">{{item.title}}
                            
                        </v-list-item-title>

                        <v-list-item-subtitle style="width:50px; margin-left:10px">{{nickname}} <br>
                         <div style="margin-top:10px; margin-left:5px"> {{getTime(item.date)}}</div> </v-list-item-subtitle>
                        <!-- <v-list-item-subtitle>{{getTime(item.date)}}</v-list-item-subtitle> -->
                        </v-list-item-content>
                        </v-list-item>
                            <v-col cols="12" sm="3">
                                <div v-for="star in item.count_star" :key="star.num">
                                    <v-icon style="color:red; float : left">mdi-star</v-icon>
                                </div>
                                <div v-for="star in (5-item.count_star)" :key="star.num">
                                    <v-icon style="float : left">mdi-star</v-icon>
                                </div>
                            </v-col>
                            <br>
                            <v-card-text>
                                {{item.content}}
                                
                                <img v-if="item.image!=='null'" v-bind:src="item.image"  style="width:100%; heigh:auto; ">
                            <br>
                            <br><br><hr><br>
                            주소 : {{item.address}} 
                            </v-card-text>
                            <hr>
                        <v-spacer></v-spacer>

                    <div style="width:100%">
                    <div style="margin-bottom:10px; margin-top:10px; padding-left:5px">
                        <div style="width:33%; float:left;">

                        <button class="animated rubberBand" v-if="like[index]===true" @click="toggledelete(item.num, index)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                        <button v-if="like[index]===false" @click="toggleadd(item.num, index)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                        </div>
                        <div style="width:33%; float:left; text-align:center; margin-top:3px">

                        <button @click="commentview(item.num, index)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                        </div>
                        <div style="width:33%; float:left; text-align:right; padding-right:10px; ; margin-top:3px">
                        <button><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></button>

                        </div>
                        <br>
                    </div>
                    <br>
                  
                        <div v-if="like[index]===true">
                            <p v-if="likelist[index] === 1">
                                {{nick}}님<span>이 좋아합니다.</span>
                            </p>
                            <p v-else>
                                {{nick}}님 외  {{ likelist[index] - 1 }} 명이 좋아합니다
                            </p>
                        </div>

                        <div v-if="like[index]===false">
                            <p>
                                {{ likelist[index] }} 명이 좋아합니다
                            </p>
                        </div>

                        <p>
                            {{ commentcount[index] }} 개의 댓글이 있습니다.
                        </p>

                        <div v-if="coment[index]===true">
                            <div v-for="cmt in todolist[index]" v-bind:key="cmt.id" >        
                                <div style="margin-bottom:5px" v-for="cmts in cmt" v-bind:key="cmts.id" >
                                    <h5 style="float:left; margin-left:5px; margin-right:20px; font-weight:bold;"> {{ cmts.nickname }}</h5> &nbsp; 
                                    <h5 style="float:left; ">{{ cmts.comment }} 
                                    </h5>
                                    <span style="float:right; margin-right:20px; font-weight:lighter; color:red" v-if="cmts.author==mynum || item.author == mynum" @click="removeComent(item.num,cmts,index)">X</span>
                                    <br>                 
                                </div>
                            </div>
                  
                            <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="comment-ok" @click="addcomment(item.num,index)"
                                :disabled="!isSubmit"
                                :class="{disabled : !isSubmit}"
                                >댓글달기</button>
                            </div>
                            <div style="margin-left:5px; width:60%;">
                                <v-text-field style="color:blue; width:90%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                </v-text-field>
                            </div>
                        </div>
                    </div>
                    
                    </v-card>
                </div>
            </div>
        </div>
    </div>
</v-app>
</template>


<script>
    import Vue from 'vue'
    import moment from 'moment'
    import VueMomentJS from 'vue-momentjs'
    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import '../../assets/css/profile.scss'
    import {mapState} from 'vuex';
    import UserApi from '../../apis/UserApi'
    import http from '../../../http-common'
    

    export default {
        created () {
           this.component = this;
            if(this.$store.state.userinfo!=null){
                this.email = this.$store.state.userinfo.email;
                this.nickname = this.$store.state.userinfo.nickName;
            }
        },
        watch : {
           
        },
        computed : {
            ...mapState(['userinfo']),
        },
        methods: {

           
        },
        data: () => {
            return {
               
            }
        }
    }
</script>
