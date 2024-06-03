import { Aircraft } from '../types/Aircraft.ts'
import axios from 'axios'

export const getAllAircraft = async(): Promise<Aircraft[]> => {
    try {
        const response = await fetch('/apr/aircraft')
        if(response.ok) {
            return await response.json();
        }
        else {
            return Promise.reject(new Error('Error encountered'))

        }
    }
    catch (error) {
        return Promise.reject(error)
    }
}

export const newAircraft = async (aircraft: Omit<Aircraft, 'id'>): Promise<Aircraft> => {
    try {
        const res = await axios.post<Aircraft>('/api/aircraft', aircraft, {
            headers: {
                'Content-type': 'application/json'
            }
        });
        return res.data;
    }
    catch (error) {
        throw new Error("Unable to create new aircraft")
    }
}

export const deleteAircraft = async (id: number) => {
    try {
        const res = await axios.delete('api/aircraft/${id}')
        return res.data
    }
    catch (error) {
        throw new Error("Could not perform action")
    }
}