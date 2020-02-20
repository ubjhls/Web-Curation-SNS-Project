<template>
<v-app data-app>


    <div class="wrapC">
        <div class="wrapper" >
            <div class="profile-card js-profile-card" >
                <div class="profile-card__cnt js-profile-cnt">
                        <div style="text-align:center; margin:auto; width:60px; height:60px">
                        <v-img @click="fileInputClick"
                        style="width:60 px;height:60px;border-radius:50%; text-align:center; background-size:auto"
                        :src="this.picture"></v-img>
                        </div>
            <v-row justify="center">
                <v-dialog
                v-model="picturedialog"
                max-width="290"
                >
                <v-card>
            <v-card-title>
            <v-file-input 
            v-model="chosenFile"
            @change="updatePicture($event)"
            label="File input"
            ></v-file-input>
            </v-card-title>
            <v-card-actions>
              <v-btn btn btn--ok color="green darken-1" text @click="submit" :disabled="dialogResult===false" :class="{disabled : !picturedialog}">변경</v-btn>
              <v-btn color="green darken-1" text @click="picturedialog = false">취소</v-btn>
              <div v-if="picture==='https://i.imgur.com/JFutv5P.png'">
                <v-btn color="green darken-1" text @click="deletePicture" disabled="">삭제</v-btn>
              </div>
              <div v-else>
                <v-btn color="green darken-1" text @click="deletePicture">삭제</v-btn>
              </div>

            
            </v-card-actions>

                </v-card>
                </v-dialog>
            </v-row>
                        <div class="profile-card__name">{{nickname}}</div>
                        <div class="profile-card__txt">{{intro}}</div>


                    <div class="profile-card-inf">
                        <div class="profile-card-inf__item" @click="goFollowerPage">
                            <div class="profile-card-inf__title">{{follower}}</div>
                            <div class="profile-card-inf__txt">Followers</div>
                        </div>

                        <div class="profile-card-inf__item" @click="goFollowingPage">
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
                <div v-else>
                 <div v-for="(item,index) in list" v-bind:key="item.id">  
                <div v-if="item.type==='스크랩'">
                    <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="margin-bottom:40px; position:relative"
                    >
                    <v-list-item>
                        <v-list-item-avatar style="height:50px; width:50px">
                            <div v-if="picture">
                                <img :src="picture" style="height:50px; width:50px">
                            </div>
                            <div v-else>
                                <v-btn color="warning" fab x-large dark>
                                <v-icon>mdi-account-circle</v-icon>
                                </v-btn>
                            </div>
                        </v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-left:5px; margin-top:5px; float:left;">
                            <div style="float:left; font-size:16px">
                            {{item.title}}
                            </div>
                            <div v-if="item.author === mynum">
                            <v-menu offset-y style="float:right;">         
                            <template v-slot:activator="{ on }">
                                <v-btn icon v-on="on" style="float:right">
                                    <v-icon style="float:right">mdi-dots-vertical</v-icon>
                                </v-btn>
                            </template>
                            
                            <v-list>
                                <v-list-item>
                                    <button style="float:right" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image,item.type)">수정</button>
                                </v-list-item>
                                <v-list-item>
                                    <button style="float:right" @click="removeFeed(item.num, index)">삭제</button>
                                </v-list-item>
                            </v-list>
                            </v-menu>
                            </div>
                        </v-list-item-title>

                        <v-list-item-subtitle style="width:50px; margin-left:5px">{{nickname}} <br>
                         <div style="margin-top:10px; margin-left:2px"> {{getTime(item.date)}}</div> </v-list-item-subtitle>
                        <!-- <v-list-item-subtitle>{{getTime(item.date)}}</v-list-item-subtitle> -->
                        </v-list-item-content>
                        </v-list-item>
                           <div style="margin-left:20px; margin-bottom:20px" v-html="item.content">
                                <br>
                            </div>
                            <v-card style="margin-left:13px; width:90%; height:auto;" @click.stop="showScrapPost(item.scrapnum, item.scarpnick)">
                                <div style=" background-color:#F7A937;text-align:center">스크랩한 게시물</div>
                                <v-list-item style="width:100%;">
                                <v-list-item-avatar style="height:50px; width:50px">
                                    <div v-if="item.scrappicture">
                                        <img :src="item.scrappicture" style="height:50px; width:50px">
                                    </div>
                                    <div v-else>
                                        <v-btn color="warning" fab x-large dark>
                                    <v-icon>mdi-account-circle</v-icon>
                                    </v-btn>
                                    </div>
                                </v-list-item-avatar>
                                <v-list-item-content style="padding-left:5%">
                                <v-list-item-title style="margin-top:5px; font-size:15px;">{{item.scraptitle}}
                                <v-list-item-subtitle>{{item.scarpnick}} <br>
                                </v-list-item-subtitle>
                                </v-list-item-title>
                                </v-list-item-content>
                                </v-list-item>

                                <div style="height:auto; overflow:hidden; text-overflow:ellipsis; white-space: nowrap; padding:5%;">{{item.scrapcontent}}</div>

                                <v-img 
                                v-if="item.image!==null"
                                style="width:100%; height:200px;"
                                :src="item.image"
                                class="white--text align-end"
                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                >
                                </v-img>

                                <v-card-actions>
                                <v-spacer></v-spacer>
                                </v-card-actions>
                            </v-card>
                        <v-spacer></v-spacer>


                    <div style="width:100%">
                    <div style="margin-bottom:10px; margin-top:15px; padding-left:5px">
                        <div style="width:50%; float:left; text-align:center;">

                        <button class="animated rubberBand" v-if="like[index]===true" @click="toggledelete(item.num, index)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                        <button v-if="like[index]===false" @click="toggleadd(item.num, index)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                        </div>
                        <div style="width:50%; float:left; text-align:center; margin-top:3px">

                        <button @click="commentview(item.num, index)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                        </div>
                          <!-- <div style="width:33%; float:left; text-align:right; padding-right:10px; ; margin-top:3px">
                        <button @click="noscrap"><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></button>

                        </div> -->
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
                                <div style="margin-bottom:10px;" v-for="cmts in cmt" v-bind:key="cmts.id" >
                                    <h5 style="width:25%; float:left; margin-left:5px; margin-right:20px; font-weight:bold; line-height:1em;"> {{ cmts.nickname }}</h5> &nbsp; 
                                    <div style="float:left; width:60%; height:auto; font-weight:lighter; line-height:1em;">{{ cmts.comment }} 
                                    </div>
                                    <span style="width:5%; float:right; font-weight:lighter; color:red; line-height:1em;" v-if="cmts.author==mynum || item.author == mynum" @click="removeComent(item.num,cmts,index)">X</span>
                                    <br>
                                    <div style="clear:both;"></div>
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

                <div class="text-center">
                    <v-dialog
                    v-model="scrapdialog"
                    width="500"
                    :retain-focus="false"
                    >
                    <v-card>
                        <v-list-item style="width:100%;">
                            <v-list-item-avatar style="height:50px; width:50px;" @click="goProfileByNickname(scrapnickname)">
                                <div v-if="scrappost.picture">
                                        <img :src="scrappost.picture" style="height:50px; width:50px;">
                                    </div>
                                    <div v-else>
                                        <v-btn color="warning" fab x-large dark>
                                    <v-icon>mdi-account-circle</v-icon>
                                    </v-btn>
                                    </div>
                            </v-list-item-avatar>
                            <v-list-item-content style="padding-left:5%">
                            <v-list-item-title style="margin-top:5px; font-size:15px;">{{scrappost.title}}
                            <v-list-item-subtitle>{{scrapnickname}} <br>
                            </v-list-item-subtitle>
                            </v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    
                        <v-col cols="12" v-if="scrappost.count_star">
                            <div v-for="star in scrappost.count_star" :key="star.num">
                                <v-icon style="color:red; float : left">mdi-star</v-icon>
                            </div>
                            <div v-for="star in (5-scrappost.count_star)" :key="star.num">
                                <v-icon style="float : left">mdi-star</v-icon>
                            </div>
                        </v-col>

                        <br>
                        <div style="margin:5%;">
                        {{scrappost.content}}
                        </div>

                        <v-img 
                        v-if="scrappost.image!==null"
                        style="margin:auto; width:100%; height:auto;"
                        :src="scrappost.image"
                        class="white--text align-end"
                        gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                        >
                        </v-img>

                        <div v-html="scrappost.address"></div>

                        <v-divider style="margin:5%;"></v-divider>

                        <div style="width:100%">
                            <div style="margin-bottom:10px; margin-top:15px; padding-left:5px">
                                <div style="width:33%; float:left;">

                                <button class="animated rubberBand" v-if="scrapLike[0]==true" @click="scraptoggledelete(scrappost.num)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                                <button v-if="scrapLike[0]==false" @click="scraptoggleadd(scrappost.num)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                                </div>
                                <div style="width:33%; float:left; text-align:center; margin-top:3px">

                                <button @click="scrapcommentview(scrappost.num)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                                </div>
                                <v-row style="backgroud:white; float:right; margin-right:2px;" justify="center">
                                    <v-dialog v-model="dialog" persistent max-width="290" :retain-focus="false">
                                    <template v-slot:activator="{ on }">
                                        <v-btn depressed color="white" v-on="on" @click="modal(scrappost.num)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></v-btn>
                                    </template>
                                    <v-card>
                                        <v-card-title>{{scrappost.nickname}}님의 게시물</v-card-title>
                                        <v-text-field style="color:blue; width:90%; margin-left:10px" label="제목입력" v-model="scraptitle" id="scraptitle"
                                        counter
                                        maxlength="13"
                                        full-width
                                        single-line>
                                        </v-text-field>
                                        <v-textarea style="color:blue; width:90%; margin-left:10px" label="내용입력" v-model="scrapcontent" id="scrapcontent"
                                        counter
                                        maxlength="50"
                                        full-width
                                        single-line></v-textarea>
                                        <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
                                        <v-btn color="green darken-1" text @click="scrapfeed(modalnum, scraptitle, scrapcontent);">스크랩</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                    </v-dialog>
                                </v-row>
                                <br>
                            </div>
                            <br>
                        
                                <div v-if="scrapLike[0]==true">
                                    <p v-if="scrapLikeCount[0] == 1">
                                        {{nick}}님<span>이 좋아합니다.</span>
                                    </p>
                                    <p v-else>
                                        {{nick}}님 외  {{ scrapLikeCount[0] - 1 }} 명이 좋아합니다
                                    </p>
                                </div>

                                <div v-if="scrapLike[0]==false">
                                    <p>
                                        {{ scrapLikeCount[0] }} 명이 좋아합니다
                                    </p>
                                </div>

                                <p>
                                    {{ scrapCommentCount[0] }} 개의 댓글이 있습니다.
                                </p>

                                <div v-if="isClickScrapComment[0]==true">
                                <div style="margin-bottom:10px;" v-for="cmts in scrapComment" v-bind:key="cmts.id" >
                                    <h5 style="width:25%; float:left; margin-left:5px; margin-right:20px; font-weight:bold; line-height:1em;"> {{ cmts.nickname }}</h5> &nbsp; 
                                    <div style="float:left; width:60%; height:auto; font-weight:lighter; line-height:1em;">{{ cmts.comment }} 
                                    </div>
                                    <span style="width:5%; float:right; font-weight:lighter; color:red; line-height:1em;" v-if="cmts.author==mynum || scrappost.author == mynum" @click="scrapremoveComent(scrappost.num,cmts)">X</span>
                                    <br>
                                    <div style="clear:both;"></div>
                                </div>
                        
                                    <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                        <button style="height:30px;" class="comment-ok" @click="scrapaddcomment(scrappost.num)"
                                        :disabled="!isSubmit"
                                        :class="{disabled : !isSubmit}"
                                        >게시</button>
                                    </div>
                                    <div style="margin-left:5px; width:60%;">
                                        <v-text-field style="color:blue; width:90%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                        </v-text-field>
                                    </div>
                                </div>
                            </div>
<br>
                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click="closeModal">close</v-btn>
                        </v-card-actions>
                    </v-card>
                    </v-dialog>
                </div>

                <div v-if="item.type==='일반'">
                    <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="margin-bottom:40px; position:relative"
                    >
                    <v-list-item>
                        <v-list-item-avatar style="height:50px; width:50px">
                            <img :src="picture" style="height:50px; width:50px">
                            </v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-top:5px; font-size:10px;">
                            <div style="float:left; font-size:16px;">
                            {{item.title}}
                            </div>
                            <v-menu offset-y style="float:right;">
                            <template v-slot:activator="{ on }">
                                <v-btn icon v-on="on" style="float:right">
                                    <v-icon>mdi-dots-vertical</v-icon>
                                </v-btn>
                            </template>
                            <v-list>
                                <v-list-item>
                                    <button style="float:right" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image,item.type)">수정</button>
                                </v-list-item>
                                <v-list-item>
                                    <button style="float:right" @click="removeFeed(item.num, index)">삭제</button>
                                </v-list-item>
                            </v-list>
                            </v-menu>
                        </v-list-item-title>

                        <v-list-item-subtitle style="width:50px; margin-left:5px">{{nickname}} <br>

                         <div style="margin-top:10px; margin-left:2px"> {{getTime(item.date)}}</div> </v-list-item-subtitle>
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
                            <v-card-text v-html="item.content">
                            </v-card-text>
                                <img v-if="item.image!=='null' || item.image!==null" v-bind:src="item.image" style="width:100%; height:200px">
                            <br>
                            주소 : {{item.address}} 
                            <!-- </v-card-text> -->
                            <hr>
                        <v-spacer></v-spacer>

                    <div style="width:100%">
                    <div style="margin-bottom:10px; margin-top:10px; padding-left:5px">
                        <div style="width:50%; float:left; text-align:center;">

                        <button class="animated rubberBand" v-if="like[index]===true" @click="toggledelete(item.num, index)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                        <button v-if="like[index]===false" @click="toggleadd(item.num, index)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                        </div>
                        <div style="width:50%; float:left; text-align:center; margin-top:3px">

                        <button @click="commentview(item.num, index)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                        </div>
                        <!-- <div style="width:33%; float:left; text-align:right; padding-right:10px; ; margin-top:3px">
                        <button @click="scrapfeed(item.num, index)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></button>
                        </div> -->
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

                        <p style="margin-bottom:3px">
                            {{ commentcount[index] }} 개의 댓글이 있습니다.
                        </p>

                        <div v-if="coment[index]===true">
                            <div v-for="cmt in todolist[index]" v-bind:key="cmt.id" >        
                                <div style="margin-bottom:10px;" v-for="cmts in cmt" v-bind:key="cmts.id" >
                                    <h5 style="width:25%; float:left; margin-left:5px; margin-right:20px; font-weight:bold; line-height:1em;"> {{ cmts.nickname }}</h5> &nbsp; 
                                    <div style="float:left; width:60%; height:auto; font-weight:lighter; line-height:1em;">{{ cmts.comment }} 
                                    </div>
                                    <span style="width:5%; float:right; font-weight:lighter; color:red; line-height:1em;" v-if="cmts.author==mynum || item.author == mynum" @click="removeComent(item.num,cmts,index)">X</span>
                                    <br>
                                    <div style="clear:both;"></div>
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
                <infinite-loading style="margin-top:0px" @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
            </div>
             
        </div>
    </div>
    <div style="margin-bottom:50px"></div>
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
    import Axios from 'axios'
    import http from '../../../http-common'
    import NavigationBar from '../../components/common/NavigationBar'
    import {fireDB} from '../../main'
    import firebase from 'firebase'
    import InfiniteLoading from 'vue-infinite-loading';
    

    export default {
        name: 'App',
        props:['propsNickname'],
        created () {
            if(this.$store.state.userinfo!=null) {
                this.myEmail = this.$store.state.userinfo.email
                this.nick = this.$store.state.userinfo.nickName
                http.get("/user/userinfo/{nickname}?nickname="+this.nick)
                .then(Response => {
                    this.mynum = Response.data.num;
                })
                .catch(Error => {
                    console.log(Error)
                })
            
            }
            
            this.nickname = this.propsNickname;
            //포스트 불러오기
            this.getUserByNickname(this.nickname);

            //검색한 사용자와 팔로잉 체크
            this.followcheck(this.nickname);

            //프로필 불러오기
            this.getProfile(this.nick);
            
        },
        watch : {
            newcomment: function(v) {
                this.checkForm();
            },
 
        },
        computed : {
            ...mapState(['userinfo']),
        },
        methods: {
            checkForm() {
                if (this.newcomment.length < 1)
                    this.error.newcomment = '1자이상'
                else
                    this.error.newcomment = false
                
                let isSubmit = true
                Object
                .values(this.error)
                .map(v => {
                    if (v)
                        isSubmit = false;
                    }
                )
                this.isSubmit = isSubmit;
            },
            getTime(time) {
                moment.locale('ko')
                return moment(time).fromNow();
            },
             getUserByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname="+nick)
                .then(Response => {
               
                    this.num = Response.data.num;
                    this.intro = Response.data.intro;
                    this.email = Response.data.email;
                    this.auth = Response.data.auth;
                    
                    //팔로잉 내역 불러오기
                    this.getFollowing(this.email);
                    //팔로워 내역 불러오기
                    this.getFollower(this.email);
                    //검색한 사용자의 피드 다불러오기
                    this.getPostByNum(this.num);
                    //this.getAlarmFromFirebase();
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getFollower() { //팔로우 정보가져오기
                let form = new FormData()
                http.get("/follow/follower?email="+this.email)
                .then(Response => {
                this.follower = Response.data;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getFollowing() { //팔로잉정보가져오기
                let form = new FormData()
                http.get("/follow/following?email="+this.email)
                .then(Response => {
                this.following = Response.data;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getPostByNum(num) { //포스트가져오기
                let form = new FormData()
                form.append('num', num)

                this.like = [];
                this.likelist = [];
                this.coment = [];
                this.todolist = [];
                this.commentcount = [];
                
                http.get("/post/post/{num}?num="+num + '&email=' + this.$store.state.userinfo.email)
                .then(Response => {
                    this.post = Response.data.object;
                    console.log(this.post)
                    //좋아요와 댓글 토글용 배열 생성
                    for (let index = 0; index < this.post.length; index++) {
                     
                        if(this.post[index].islike==1){
                            this.like.push(true)
                        }else{
                            this.like.push(false)
                        }
                        this.likelist.push(this.post[index].count_like);
                        this.coment.push(false)
                        this.todolist.push([])
                        this.commentcount.push(this.post[index].count_comment)


                        this.post[index].content = this.post[index].content.replace(/(?:\r\n|\r|\n)/g, '<br/>');
                    }
                    if(this.post.length!=0){
                        this.infiniteHandler(this.state);
                    }
                    
                })
            },
            followcheck(nick) { //보는 유저와 팔로우 되어 있는지 확인하기
                let myn  = this.$store.state.userinfo.nickName;
 
                http.get("/follow/follow?mynickname=" + myn + "&nickname=" + nick)
                .then(Response => {
                    if(Response.data==0){
                       
                        this.isfollow = 0;
                    }else if(Response.data==1){
                       
                        this.isfollow = 1;
                        
                    }else{
                        this.isfollow = -1;
                          //내가 나의 정보를 검색할때
                        if(this.nick==this.nickname){
                            this.isfollow=1;
                         }
                    }
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            unfollowgo(){ //팔로우해제하기
                let form = new FormData()
                let myn  = this.$store.state.userinfo.nickName;
                form.append('mynickname', myn)
                form.append('nickname',this.nickname)
                http.post("/follow/unFollow", form)
                .then(Response => {
                    this.isfollow = 0;
                    this.getFollower();
                    this.getFollowing();
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            followgo(){ //팔로우신청하기
                if(this.auth==0) {
                    let form = new FormData();
                    let myn  = this.$store.state.userinfo.nickName;
                    form.append('mynickname', myn)
                    form.append('nickname',this.nickname)
                    http.post("/follow/follow", form)
                    .then(Response => {
                        this.isfollow = 1;
                        this.getFollower();
                        this.getFollowing();
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                }
                else if(this.auth==1) {
                    let form = new FormData();
                    let myn  = this.$store.state.userinfo.nickName;
                    form.append('mynickname', myn)
                    form.append('nickname',this.nickname)
                    
                    http.post("/follow/nonfollow", form)
                    .then(Response => {
                        if(Response.data==='success') {
                            alert("팔로우가 요청되었습니다.")
                        }
                        else if(Response.data==='failed') {
                            alert("이미 팔로우 신청을 하였습니다.")
                        }
                    })
                    .catch(Error => {
                        console.log(Error)
                    })

                }
            },
            goFollowerPage() {
                this.$router.push({name : 'FollowPage'})
            },
            goFollowingPage() {
                this.$router.push({name : 'FollowingPage'})
            },
            toggleadd(num, index) { //좋아요를 클릭할때
               this.likelist[index]++;
               this.$set(this.like,index,!this.like[index])
                
                //좋아요 서버로 전송하기
                let form = new FormData()
                form.append('postnum', num)
                form.append('email', this.$store.state.userinfo.email)

                let modalEmail = null;

                http.get("/user/userinfo/{nickname}?nickname="+this.list[index].nickname)
                .then(Response => {
                    console.log("scrapnick " + this.scrappost.nickname)
                    modalEmail = Response.data.email
                })
                .catch(Error => {
                    console.log(Error)
                })

                http.post('/postlike/like',form)
                .then(response => {
                     this.post = response.data.object; 
                     if(this.mynum!=this.list[index].author) {
                        this.updateAlarmToFirebase(modalEmail)
                     }
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            toggledelete(num, index) { //좋아요를 해제할때
                this.likelist[index]--;
                this.$set(this.like,index,!this.like[index])

                http.delete("/postlike/unlike?postnum="+num + '&email=' + this.$store.state.userinfo.email)
                .then(response => {
                     this.post = response.data.object
                     
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            commentview(num,index){ //댓글 버튼 누를 때
            //댓글 불러오기
            if(this.coment[index]==false){
                    http.get('/comment/comment?postnum='+num)
                .then(response => {
                    if(response.data.object!=null){
                        this.todolist[index].push(response.data.object)
                    } 
                })
                .catch(Error => {
                    console.log(Error)
                })

            // //댓글 숨기기
             }
            else if(this.coment[index]==true){
                 
                    this.$delete(this.todolist[index],0);
                }
                this.$set(this.coment,index,!this.coment[index])

                //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        
                        this.$set(this.commentcount,index,Response.data)
                        
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                    
            }
            ,
            addcomment(num,index) {

                let form = new FormData()
                form.append('comment', this.newcomment)
                form.append('email', this.$store.state.userinfo.email)
                form.append('postnum', num)
                http.post("/comment/comment", form)
                .then(response => {
                   
                    //댓글 등록
                    this.$delete(this.todolist[index],0);
                    this.todolist[index].push(response.data.object)

                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                    
                        this.$set(this.commentcount,index,Response.data)
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                    
                    //댓글 초기화
                    this.newcomment=''
                })
                
            },
            removeComent(num, cmt, index){
                http.delete("/comment/comment?num=" + cmt.num + "&postnum=" + num)
                .then(response => {
                    //댓글 삭제(갱신까지)
                    this.$delete(this.todolist[index],0);
                    this.todolist[index].push(response.data.object)
                    
                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        
                        this.$set(this.commentcount,index,Response.data)
                        
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                })
                .catch(Error =>{
                })
 
                
            },
            
            removeFeed(num, index){
                 if (confirm("정말 삭제하시겠습니까??") == true){    //확인
                    http.delete("/post/post?num=" + num + "&mynum=" + this.$store.state.userinfo.num)
                    .then(response => {
                        alert('게시물이 삭제되었습니다.')
                        this.post = response.data.object;
                        this.list.splice(index, 1);
                    })
                .catch(Error =>{
                })

                }else{   //취소
                    return false;
                }

            },
            updateFeed(num, title, content, count_star, address, image, type){
                var router = this.$router
                 router.push({
                    name: "UpdateFeed",
                    params: {
                        "type": type,
                        "num": num,
                        "title": title,
                        "content": content,
                        "address": address,
                        "count_star": count_star,
                        "image": image
                    }
                });
            },

            //밑은 알람 메소드
            setAlarm(alarm) {
                this.userAlarmCount = alarm;
            },
            updateAlarmToFirebase(email) {       
                fireDB.collection('Alarm').doc(email)
                .update({
                    count : firebase.firestore.FieldValue.increment(1)
                })
            },
            getAlarmFromFirebase() {
                let whoami = this;
                let count = 0;
                fireDB.collection('Alarm').doc(this.email).get().then(function(doc) {
                if(doc.data()==undefined) {
                    count = 0;
                } else {
                    count = doc.data().count;
                }
                whoami.setAlarm(count);
                }).catch(function(error) {
                    console.log(error)
                })
            },
            watchAlarmFromFirebase() {
                let whoami = this;
                let count=0;
                fireDB.collection('Alarm').doc(this.email).onSnapshot( {
                    includeMetadataChanges: true    
                },function(doc) {
                    if(doc.data()==undefined) {
                        count = 0;
                    } else {
                        count = doc.data().count;
                    }
                    whoami.setAlarm(count);
                })
            },
            togglecomment(num) {
                this.commenttoggle = !this.commenttoggle
                http.get("/comment/comment?num=" + num)
                .then(response => {
                    this.comment = response.data.object
                    
                })
                .catch(Error => {
                    console.log(Error)
                })
                alert("댓글이 등록되었습니다.")
               
            },
            scrapfeed(num,title,content) {
                this.dialog= false
                let form = new FormData()
                form.append('postnum', num)
                form.append('title',title)
                form.append('content',content)
                form.append('num',this.$store.state.userinfo.num)
                http.post("/post/scrap", form)
                // console.log(num)
                .then(Response => {
                    alert("게시물이 스크랩되었습니다.")
                    this.getPostByNum(this.num);
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            updatePicture(event){
                this.picturedialog = event
                let formdata = new FormData
                formdata.append('image', this.picturedialog)
                Axios.post('https://api.imgur.com/3/image',formdata, {headers:{Authorization: 'Client-ID d15c5b033075c6e'}})
                .then(Response => {
                    this.dialogResult = Response.data.data.link
                    
                })
                .catch(Error =>{

                })
            },
            modal(num){
                this.modalnum = num
            },
            // //무한 스크롤 메소드
             infiniteHandler($state){    
                this.state = $state
               if(this.post.length!=0){
                setTimeout(()=>{
                    //alert("ㅎㅇ")
             
                    const temp = [];
                    const size = this.list.length;
                    for (let i = size; i< size+3; i++) {
                        if(this.post[i]!=null){
                            temp.push(this.post[i]);
                        }
                    }
                    this.list = this.list.concat(temp);
                    console.log(this.list)
                    $state.loaded();
                 
                    if(this.list.length==this.post.length){
                        $state.complete();
                  
                    }
                    
                },1000)
               }
            },
            noscrap() {
                alert("이미 스크랩 된 게시물입니다.")
            },
            submit(){
                this.picturedialog = false
                let form = new FormData()
                form.append('num', this.mynum)
                form.append('picture', this.dialogResult)
                http.post('profile/insertPicture', form)
                .then(Response => {
                    if(Response.data.data==='success'){
                        this.getProfile(this.nick)
                    }
                    
                })
            },
            fileInputClick(){
                this.picturedialog = true;
                this.chosenFile = null;
                this.dialogResult = false;
            },
            deletePicture(){
                let num = this.mynum
                http.get("profile/deletePicture/?num=" + num)
                .then(Reponse =>{
                    // console.log(Response)
                    this.picture = '';
                    this.picturedialog = false
                })
                .catch(Error => {
                    console.log(Error)
                })
                
            },
            getProfile(nick){
                http.get("/profile/profile/?nickname=" + nick)
                .then(Response => {
                    this.picture = Response.data.picture;
                    // console.log(this.picture)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            showScrapPost(scrapNum, scrapnickname) {
                this.scrapdialog = true;
                this.scrapnickname = scrapnickname;
                this.scrapLike = [];
                this.scrapLikeCount = [];
                this.isClickScrapComment = [];
                this.scrapCommentCount = [];

                 http.get('/post/post/{postnum}?num=' + scrapNum +'&email=' + this.myEmail)
                .then(Response => {
                    this.scrappost = Response.data.object;
                    console.log(this.scrappost)
                    if(this.scrappost!=null) {
                        if(this.scrappost.islike==1) {
                            this.scrapLike.push(true);
                        } else {
                            this.scrapLike.push(false);
                        }
                        this.scrapLikeCount.push(this.scrappost.count_like);
                        this.isClickScrapComment.push(false);
                        this.scrapComment.push([]);
                        this.scrapCommentCount.push(this.scrappost.count_comment);
                    }
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            scraptoggleadd(num) {
                this.scrapLikeCount[0]++;
                this.$set(this.scrapLike, 0, !this.scrapLike[0])
                console.log(this.scrapLike[0])
                //좋아요 서버로 전송하기
                let form = new FormData()
                form.append('postnum', num)
                form.append('email', this.$store.state.userinfo.email)

                let modalEmail = null;

                http.get("/user/userinfo/{nickname}?nickname="+this.scrappost.nickname)
                .then(Response => {
                    modalEmail = Response.data.email
                })
                .catch(Error => {
                    console.log(Error)
                })

                http.post('/postlike/like',form)
                .then(response => {
                    if(this.mynum!=this.scrappost.author) {
                        this.updateAlarmToFirebase(modalEmail)
                    }
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            scraptoggledelete(num) { //좋아요를 해제할때
                this.scrapLikeCount[0]--;
                this.$set(this.scrapLike, 0, !this.scrapLike[0])

                http.delete("/postlike/unlike?postnum="+num + '&email=' + this.$store.state.userinfo.email)
                .then(response => {
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            scrapcommentview(num) {
                //댓글 불러오기
                this.newcomment=''
                if(this.isClickScrapComment[0]==false){
                    http.get('/comment/comment?postnum='+num)
                    .then(response => {
                        if(response.data.object!=null){
                            this.scrapComment = response.data.object;
                            console.log(this.scrapComment)
                        } 
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                }
                //댓글 숨기기
                else if(this.isClickScrapComment==true){
                    this.scrapComment = [];
                }
                this.$set(this.isClickScrapComment, 0, !this.isClickScrapComment[0]);
                //댓글 수 갱신
                http.get("/comment/count?postnum="+num)
                .then(Response => {
                    this.scrapCommentCount[0] = Response.data;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            scrapaddcomment(num) {
                let form = new FormData()
                form.append('comment', this.newcomment)
                form.append('email', this.$store.state.userinfo.email)
                form.append('postnum', num)
                http.post("/comment/comment", form)
                .then(response => {
                   
                    //댓글 등록
                    this.scrapComment = response.data.object;

                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        this.$set(this.scrapCommentCount,0,Response.data)
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                    
                    //댓글 초기화
                    this.newcomment=''
                })
            },
            scrapremoveComent(num, cmt) {
                http.delete("/comment/comment?postnum=" + num + "&num="+ cmt.num + "&nickname=" + cmt.nickname + "&date=" + cmt.date)
                .then(response => {
                    //댓글 삭제(갱신까지) 
                    this.scrapComment = response.data.object;
                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        this.$set(this.scrapCommentCount,0,Response.data)
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                })
                .catch(Error =>{
                })
            },
            closeModal() {
                this.scrapdialog = false;
                this.list.unshift(this.post[0]);
                // this.getPostByNum(this.num);
            },
            goProfileByNickname(nick) {
                this.scrapdialog = false;
                this.$emit('child', nick)
            },
        },
        data: () => {
            return {
                modalnum:0,
              
                usernum:0,
                isSubmit: false,
                error:{
                    comment:false
                },
                scraptitle:'',
                scrapcontent:'',
                nick:'',
                nickname : '',
                num:0,
                intro:'',
                auth:1,
                email:'', 
                follower:0,
                following:0,
                isfollow:0,
                like:[],
                coment:[],
                todolist:[],
                newcomment: "",
                myEmail:'',
                feeds: 0,
                post : [],
                commentNum:'',
                userAlarmCount: 0,
                likelist:[],
                mynum:0,
                commentcount:[],
                dialog: false,
                picturedialog: false,
                dialogResult: false,
                chosenFile:null,
                picture:'',
                list:[],
                scrappost :[],
                scrapnickname:'',
                scrapdialog : false,
                scrapComment:[],
                scrapCommentCount:[],
                scrapLike:[],
                scrapLikeCount:[],
                isClickScrapComment:[],
            }
        },
        components:{
            InfiniteLoading
        }
    }
</script>
<style lang="scss" scoped>
#app p {
    margin-left:5px;
    margin-bottom: 1px;
    color: gray;
    font-size:12px;
}

</style>
