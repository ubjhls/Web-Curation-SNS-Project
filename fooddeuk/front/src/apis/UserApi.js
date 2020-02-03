import Axios from "axios";
import http from '../../http-common'
/*
 User API 예시
 */
// const requestLogin = (data,callback,errorCallback) => {
//     //백앤드와 로그인 통신하는 부분
//     let form = new FormData()
//     form.append('email', data.email)
//     form.append('password', data.password)
    
//     Axios.post("http://192.168.31.103:8080/account/login", form)
//         .then(Response => {
//             console.log("response : ", JSON.stringify(Response, null, 2));
//             if(Response.data.data == "success"){
//                 callback("success", callback);
//             } 
//             else if(Response.data.data === "noemailcheck") {
//                 callback("noemailcheck", callback);
//             }
//             else {
//                 callback("failed", callback);
//             }
//         })
//         .catch(Error => {
//             console.log("failed", errorCallback);
//             errorCallback();
//         })
// }
const requestCheckNick = (data, callback, errorCallback) => {
    //백앤드와 닉네임 중복체크 통신하는 부분
    
    return http.get('/account/nickname?nickname='+ data.nickName).then( response => {
            callback(response.data.status);
            // console.log('response', response.data);
        }).catch( error => {
            errorCallback();
            console.log('failed', error);
        });
}
const requestCheckEmail= (data, callback, errorCallback) => {
    //백앤드와 닉네임 중복체크 통신하는 부분
    
    return http.get('/account/checkEmail' + data.email).then( response => {
            callback(response.data.status);
            // console.log('response', response.data);
        }).catch( error => {
            errorCallback();
            console.log('failed', error);
        });
}
// const requestFollower = (data, callback, errorCallback) => {
//     let form = new FormData();
//     form.append('count_follower', data.count_follower)

//     return Axios.post('http://192.168.31.103:8080/',
// }
const requestJoin = (data,callback,errorCallback) => {
    http.post("/account/user", {
        email:data.email,
        nickname:data.nickName,
        name:data.name,
        password:data.password,
        intro:data.intro,
    })
        .then(Response => {
            // console.log("response : ", JSON.stringify(Response, null, 2));
            if(Response.data.data == "hasNick") {
                callback("hasNick", callback);
            } else if(Response.data.data == "hasEmail") {
                callback("hasEmail", callback);
            } else if(Response.data.data == "success") {
                callback("success", callback);
            } else {
                callback("failed", callback);
            }
        })
        .catch(Error => {
            console.log("failed", errorCallback);
            errorCallback();
        })
}

const requestFindPassword = (data,callback,errorCallback) => {
        let form = new FormData()
        form.append('email', data)
        http.post("/mail/certification", form)  //비밀번호 찾기시 이메일 보내기  이거해야댐!!!!   //추가해야됨 post(/mail/password) {email}
        .then(Response => {
            // console.log("response : ", JSON.stringify(Response, null, 2));
            if(Response.data.data == "success"){
                callback("success", callback);
            } else {
                callback("failed", callback);
            }
        })
        .catch(Error => {
            console.log("failed", errorCallback);
            errorCallback();
        })
}

const requestEmailConfirm = (data,callback,errorCallback) => {
    let form = new FormData()
    form.append('email', data.email)
    form.append('key', data.key)
    http.patch("/mail/confirm?email="+data.email + "&key=" + data.key)
    .then(Response => {
        // console.log(Response)
        if(Response.data == "success"){
            callback("success", callback);
        } else {
            callback("failed", callback);
        }
    })
    .catch(Error => {
        console.log("failed", errorCallback);
        errorCallback();
    })
}

const requestSendEmail = (data,callback,errorCallback) => {
    let form = new FormData()
    form.append('email', data.email)
    // console.log(data.email);
    http.post("/mail/resend", form)
    .then(Response => {
        // console.log(Response)
        if(Response.data == "success"){
            callback("success", callback);
        } else {
            callback("failed", callback);
        }
    })
    .catch(Error => {
        console.log("failed", errorCallback);
        errorCallback();
    })
}

const requestGetAllSearch= (data,callback,errorCallback) => {
    let form = new FormData()
    form.append('email', data.email)
    // console.log(data.email);
    http.get("/search/all?email=" + data.email)
    .then(Response => {
        // console.log(Response);
        if(Response!=null)
            callback(Response.data, callback);
    })
    .catch(Error => {
        console.log("failed", errorCallback);
        errorCallback();
    })
}

const requestSearchNickname= (data,callback,errorCallback) => {
    let form = new FormData()
    form.append('nickname', data.nickName)
    // console.log(data.email);
    // console.log(form.get('nickname'))
    http.get("/search/nickname?nickname=" + data.nickName)
    .then(Response => {
    //    console.log(Response)
       if(Response.data.data!='failed'){
             //alert(Response.data.object)
             callback(Response.data.object, callback)
       } else {
            callback({}, callback)
       }
       
        
    })
    .catch(Error => {
        console.log("failed", errorCallback);
        errorCallback();
    })
}

const requestDeleteSearch= (data,callback,errorCallback) => {
    // console.log(data.email);
    let form = new FormData()
    form.append('myNick', data.myNick)
    form.append('otherNick', data.otherNick)
    
    http.delete("/search/search?myNick=" + data.myNick + "&otherNick="+data.otherNick)
    .then(Response => {
        // console.log(Response)
        if(Response.data == "success"){
            callback("success", callback);
        } else {
            callback("failed", callback);
        }
    })
    .catch(Error => {
        console.log("failed", errorCallback);
        errorCallback();
    })
}

const requestModifyProfile = (data,callback,errorCallback) => {
    let form = new FormData()
    // form.append('nickname', data.nickName)
    // form.append('intro', data.intro)
    // form.append('auth', data.authData)
    // form.append('email', data.email)
    http.patch("/user/user?auth="+data.authData + "&email="+data.email + "&intro="+data.intro+"&nickname=" +data.nickName)
    .then(Response => {
        // console.log("response : ", JSON.stringify(Response, null, 2));
        // console.log(Response.data)
        if(Response.data == "success") {
            callback("success", callback);
        } else {
            callback("failed", callback);
        }
    })
    .catch(Error => {
        console.log("failed", errorCallback);
        errorCallback();
    })
}

const UserApi = {
    // requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback),
    requestJoin:(data,callback,errorCallback)=>requestJoin(data,callback,errorCallback),
    requestFindPassword:(data,callback,errorCallback)=>requestFindPassword(data,callback,errorCallback),
    requestCheckNick:(data,callback,errorCallback)=>requestCheckNick(data,callback,errorCallback),
    requestCheckEmail:(data,callback,errorCallback)=>requestCheckEmail(data,callback,errorCallback),
    requestEmailConfirm:(data,callback,errorCallback)=>requestEmailConfirm(data,callback,errorCallback),
    requestSendEmail:(data,callback,errorCallback)=>requestSendEmail(data,callback,errorCallback),
    requestSearchNickname:(data,callback,errorCallback)=>requestSearchNickname(data,callback,errorCallback),
    requestGetAllSearch:(data,callback,errorCallback)=>requestGetAllSearch(data,callback,errorCallback),
    requestDeleteSearch:(data,callback,errorCallback)=>requestDeleteSearch(data,callback,errorCallback),
    requestModifyProfile:(data,callback,errorCallback)=>requestModifyProfile(data,callback,errorCallback),
}

export default UserApi