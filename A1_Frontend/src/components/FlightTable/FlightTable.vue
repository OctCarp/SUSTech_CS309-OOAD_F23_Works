<template>
  <div id="main-div">
    <h2 id="flight-table">Flight List</h2>
    <el-table
        :data="flights"
        stripe
        style="margin: auto;"
    >
      <el-table-column prop="FlightNo" label="Flight No."></el-table-column>
      <el-table-column prop="AirlineCompany" label="Airline Company"/>
      <el-table-column prop="From" label="From"/>
      <el-table-column prop="To" label="To"/>
      <el-table-column prop="Date" label="Date"/>
      <el-table-column prop="DepartureTime" label="Departure Time"/>
      <el-table-column prop="ArrivalTime" label="Arrival Time"/>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button type="info" @click="deleteFlight(scope.$index)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-button style="margin-left: 45%; margin-top: 20px" type="primary" @click="dialogVisible = true">
      Add New Flight
    </el-button>

    <el-dialog
        v-model="dialogVisible"
        title="Add A New Flight"
        width="40%"
    >
      <el-form
          ref="flightFormRef"
          :model="flightForm"
          :rules="rules"
          label-width="auto"
          label-position="right"
          size="default"
      >

        <el-form-item label="Flight No." prop="FlightNo">
          <el-input v-model="flightForm.FlightNo"/>
        </el-form-item>
        <el-form-item label="Airline Company" prop="AirlineCompany">
          <el-input v-model="flightForm.AirlineCompany"/>
        </el-form-item>
        <el-form-item label="From" prop="From">
          <el-input v-model="flightForm.From"/>
        </el-form-item>
        <el-form-item label="To" prop="To">
          <el-input v-model="flightForm.To"/>
        </el-form-item>

        <el-form-item label="Date" prop="Date">
          <el-date-picker
              value-format="YYYY/MM/DD"
              v-model="flightForm.Date"
              type="date"
              label="Pick a date"
              placeholder="Pick a date"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="Departure Time" prop="DepartureTime">
          <el-time-picker
              format='HH:mm'
              value-format='HH:mm'
              v-model="flightForm.DepartureTime"
              label="Pick a time"
              placeholder="Pick a time"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="Arrive Time" prop="ArriveTime">
          <el-time-picker
              format='HH:mm'
              value-format='HH:mm'
              v-model="flightForm.ArriveTime"
              label="Pick a time"
              placeholder="Pick a time"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addFlight" :loading="isSubmitting">Create</el-button>
          <el-button @click="handleDialogClose">Cancel</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import type {FormInstance} from 'element-plus'
import type {Flight, FlightForm} from './types'
import {rules} from './validators'
import {presetData} from "./preset-data"

const flights = ref<Flight[]>([])
const dialogVisible = ref(false)
const flightFormRef = ref<FormInstance>()
const isSubmitting = ref(false)

const initialFormState: FlightForm = {
  FlightNo: "",
  AirlineCompany: "",
  From: "",
  To: "",
  Date: "",
  DepartureTime: "",
  ArriveTime: ""
}

const flightForm = ref<FlightForm>({...initialFormState})

const resetForm = () => {
  if (flightFormRef.value) {
    flightFormRef.value.resetFields()
  }
  flightForm.value = {...initialFormState}
}

const handleDialogClose = () => {
  resetForm()
  dialogVisible.value = false
}

const deleteFlight = (index: number) => {
  ElMessageBox.confirm('Are you sure you want to delete this flight?', 'Warning', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    flights.value.splice(index, 1)
    ElMessage.success('Deleted successfully')
  }).catch(() => {
  })
}

const addFlight = async () => {
  if (!flightFormRef.value) return

  try {
    isSubmitting.value = true
    await flightFormRef.value.validate()

    const newFlight: Flight = {
      "FlightNo": flightForm.value.FlightNo,
      "AirlineCompany": flightForm.value.AirlineCompany,
      "From": flightForm.value.From,
      "To": flightForm.value.To,
      "Date": flightForm.value.Date,
      "DepartureTime": flightForm.value.DepartureTime,
      "ArrivalTime": flightForm.value.ArriveTime
    }

    flights.value.push(newFlight)
    ElMessage.success('Flight added successfully')
    handleDialogClose()
  } catch (error) {
    ElMessage.error('Please check the form fields')
  } finally {
    isSubmitting.value = false
  }
}

onMounted(() => {
  flights.value = presetData
})
</script>

<style scoped>
#flight-table {
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  margin: 20px;
  padding: 10px;
  color: #333;
}

#main-div {
  max-width: 1200px;
  margin: auto;
  padding: 20px;
}

.el-button {
  margin-right: 10px;
}

.el-dialog {
  border-radius: 8px;
}
</style>
