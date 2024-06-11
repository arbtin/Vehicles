import { Aircraft } from '../types/Aircraft.ts'
import axios from 'axios'

export const getAllAircraft = async (): Promise<Aircraft[]> => {
    try {
        const response = await axios.get('/api/v1/aircraft')
        if (response.status !== 200) {
            return Promise.reject(new Error('Failed to fetch aircraft'))
        }
        return response.data
    } catch (error) {
        return Promise.reject(error)
    }
}

export const newAircraft = async (aircraft: Omit<Aircraft, 'id'>): Promise<Aircraft> => {
    try {
        const res = await axios.post<Aircraft>('/api/v1/aircraft', aircraft, {
            headers: {
                'Content-type': 'application/json',
            },
        })
        return res.data
    } catch (error) {
        throw new Error('Unable to create new aircraft')
    }
}

export const deleteAircraft = async (id: number) => {
    try {
        const res = await axios.delete(`api/v1/aircraft/${id}`)
        return res.data
    } catch (error) {
        throw new Error('Could not perform action')
    }
}


/*

export const submitNineLineRequest = async (newNineLine: NineLineRequestType): Promise<NineLineRequestType> => {
    if (newNineLine.id !== null) {
        return Promise.reject(new Error());
    } else if (newNineLine.location === null) {
        return Promise.reject(new Error());
    }
    try {
        const response = await axios.post("api/v1/medevac", newNineLine, {
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json"
            }
        });

        if (response.status !== 201) {
            return Promise.reject(new Error());
        }

        return response.data;
    } catch (error) {
        return Promise.reject(new Error());
    }
};



* */
