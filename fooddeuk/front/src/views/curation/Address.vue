<template>
<v-app>
  <div class="header" style="width:100%; height:40px">
            <div style="float:left;">
            <button v-on:click="goBack">
                <img src="../../assets/images/backIcon.png" style="width:35px;">
            </button>
            </div>
            <p style="vertical-align: middle;padding: 8px 5px;float:left;">뉴스피드 설정</p>
        </div>
  <v-container fluid>
    <v-row align="center">
      <v-col style="margin-top:200px; width:100%" cols="6">
        <v-subheader>지역</v-subheader>
      </v-col>
      <v-col style="margin-top:200px" cols="6">
        <v-subheader>동네</v-subheader>
      </v-col>
      <v-col cols="6">
        <v-select 
          v-model="select"
          :items="items"
          item-text="state"
          persistent-hint
          return-object
          single-line
        ></v-select>

      </v-col>
      <v-col cols="6">
        <v-select v-if="select.state ==='Florida'" v-model="selectt" :items="location[0]" item-text="state" label="Select"></v-select>
        <v-select v-if="select.state ==='서울특별시'" v-model="selectt" :items="location[0]" label="전체"></v-select>
        <v-select v-if="select.state ==='부산광역시'" v-model="selectt" :items="location[1]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='대구광역시'" v-model="selectt" :items="location[2]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='인천광역시'" v-model="selectt" :items="location[3]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='광주광역시'" v-model="selectt" :items="location[4]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='대전광역시'" v-model="selectt" :items="location[5]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='울산광역시'" v-model="selectt" :items="location[6]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='강원도'" v-model="selectt" :items="location[7]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='경기도'" v-model="selectt" :items="location[8]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='경상남도'" v-model="selectt" :items="location[9]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='경상북도'" v-model="selectt" :items="location[10]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='전라남도'" v-model="selectt" :items="location[11]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='전라북도'" v-model="selectt" :items="location[12]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='제주도'" v-model="selectt" :items="location[13]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='충청남도'" v-model="selectt" :items="location[14]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='충청북도'" v-model="selectt" :items="location[15]" item-text="state" label="전체"></v-select>
        <v-select v-if="select.state ==='없음'" v-model="selectt" :items="location[16]" item-text="state" label="전체"></v-select>
      </v-col>
      
      <v-col style="text-align:center" cols="6">
        <h2> 내지역</h2>
       {{ firstresult }}
      </v-col>
      <v-col style="text-align:center" cols="6">
        <h2> 내동네</h2>
       {{secondresult}}
      </v-col>
      <v-col cols="6">
        <div style="margin-top:200px">
            <button class="button--orange" @click="submit">수정</button>
        </div>
      </v-col>
      <v-col cols="6">
        <div style="margin-top:200px">
            <button class="button--blue" @click="goBack">취소</button>
        </div>
      </v-col>
    </v-row>
  </v-container>
</v-app>
</template>

<script>
import Axios from "axios";
import '../../assets/css/address.scss'
  export default {
    created(){
      let form = new FormData()
      form.append('email', this.$store.state.userinfo.email)
      Axios.post("http://192.168.31.103:8080/profile/getMyPlace", form)
                 .then(Response => {
                  
                    this.firstresult = Response.data.object[0];
                    this.secondresult = Response.data.object[1];
                  
                 })
                 .catch(Error => {

                 })
    },
    watch:{
        selectt:function(v){
          // this.result = v;
        }
    },
    data () {
      return {
        firstresult:'',
        secondresult:'',
        result:'없음',
        selectt: '',
        location:[
        ['강남구','강동구','강북구','강서구','관악구','광진구','구로구','금천구','노원구','도봉구','동대문구','동작구','마포구','서대문구','서초구','성동구','성북구','송파구','양천구','영등포구','용산구','은평구','종로구','중구','중랑구'],
        ['강서구','금정구','남구','동구','동래구','부산진구','북구','사상구','사하구','서구','수영구','연제구','영도구','중구','해운대구','기장군'],
        ['남구','달서구','동구','북구','서구','수성구','중구','달성군'],
        ['계양구','남구','남동구','동구','부평구','서구','연수구','중구','강화군','옹진군'],
        ['광산구','남구','동구','북구','서구'],
        ['대덕구','동구','서구','유성구','중구'],
        ['남구','동구','북구','중구','울주군'],
        ['강릉시','동해시','삼척시','속초시','원주시','춘천시','태백시','고성군','양구군','양양군','영월군','인제군','정선군','철원군','평창군','홍천군','화천군','횡성군'],
        ['고양시 덕양구','고양시 일산구','과천시','광명시','광주시','구리시','군포시','김포시','남양주시','동두천시','부천시 소사구','부천시 오정구','부천시 원미구','성남시 분당구','성남시 수정구','성남시 중원구','수원시 권선구','수원시 장안구','수원시 팔달구','시흥시','안산시 단원구','안산시 상록구','안성시','안양시 동안구','안양시 만안구','오산시','용인시','의왕시','의정부시','이천시','파주시','평택시','하남시','화성시','가평군','양주군','양평군','여주군','연천군','포천군'],
        ['거제시','김해시','마산시','밀양시','사천시','양산시','진주시','진해시','창원시','통영시','거창군','고성군','남해군','산청군','의령군','창녕군','하동군','함안군','함양군','합천군'],
        ['경산시','경주시','구미시','김천시','문경시','상주시','안동시','영주시','영천시','포항시 남구','포항시 북구','고령군','군위군','봉화군','성주군','영덕군','영양군','예천군','울릉군','울진군','의성군','청도군','청송군','칠곡군'],
        ['광양시','나주시','목포시','순천시','여수시','강진군','고흥군','곡성군','구례군','담양군','무안군','보성군','신안군','영광군','영암군','완도군','장성군','장흥군','진도군','함평군','해남군','화순군'],
        ['군산시','김제시','남원시','익산시','전주시 덕진구','전주시 완산구','정읍시','고창군','무주군','부안군','순창군','완주군','임실군','장수군','진안군'],
        ['서귀포시','제주시','남제주군','북제주군'],
        ['공주시','논산시','보령시','서산시','아산시','천안시','금산군','당진군','부여군','서천군','연기군','예산군','청양군','태안군','홍성군'],
        ['제천시','청주시 상당구','청주시 흥덕구','충주시','괴산군','단양군','보은군','영동군','옥천군','음성군','진천군','청원군'],
        ['없음']
        ],

        select: { state:'없음'},
        items: [
          { state: '서울특별시'},
          { state: '부산광역시'},
          { state: '대구광역시'},
          { state: '인천광역시'},
          { state: '광주광역시'},
          { state: '대전광역시'},
          { state: '울산광역시'},
          { state: '강원도'},
          { state: '경기도'},
          { state: '경상남도'},
          { state: '경상북도'},
          { state: '전라남도'},
          { state: '전라북도'},
          { state: '제주도'},
          { state: '충청남도'},
          { state: '충청북도'},
          { state: '충청북도'},
          { state: '없음'},
        ],
        
      }
    },
    methods :  {
      goBack() {
        var router = this.$router;
        router.go(-1)
      },
      submit() {
        let form = new FormData()
        
        form.append('email', this.$store.state.userinfo.email)
        form.append('place1', this.select.state)
        form.append('place2', this.selectt)

        Axios.post("http://192.168.31.103:8080/profile/updatePlace", form)
          .then(Response => {
            alert('지역정보가 수정되었습니다.')
            var router = this.$router;
            router.push({name:"MainPage"})
          })
          .catch(Error => {

                 })
      }
    }
  }
</script>

<style>
.button--orange {
  margin-top: 5px;
  background: linear-gradient(45deg, #000000, #000000);
}
.button--blue {
  margin-top: 5px;
  background: linear-gradient(45deg, #8d8a8a, #919191);
}
</style>