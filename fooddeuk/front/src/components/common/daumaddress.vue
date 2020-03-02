<template>
  <div id="app">
    <DaumPostcode
      :on-complete=handleAddress
    />
  </div>
</template>

<script>
import DaumPostcode from 'vuejs-daum-postcode'

var handleAddress = (data) => {
  let fullAddress = data.address
  let extraAddress = ''
  if (data.addressType === 'R') {
    if (data.bname !== '') {
      extraAddress += data.bname
    }
    if (data.buildingName !== '') {
      extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName)
    }
    fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '')
  }
}

export default {
  name: 'App',
  components: {
    DaumPostcode
  },
  methods: {
    handleAddress
  }
}
</script>