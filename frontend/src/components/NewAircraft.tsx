import { Button, TextField } from '@mui/material'
import React, { useState } from 'react'
import { saveAircraft } from '../api/AircraftApi.ts'
import { useNavigate } from 'react-router-dom'

export default function Create():  any {
    const redirect = useNavigate()

    const [name, setName] = useState('')
    const [airframe, setAirfram] = useState('')
    const [pilot, setPilot] = useState('')

    const aircraft = {
        name = name,
        airframe = airframe,
        pilot = pilot
    }

    const onSubmit = (e any) => {
        e.preventDefault();
        saveAircraft(aircraft)
        redirect('/')
    };

    return (
    <>
        <h2>New Aircraft</h2>
        <form onSubmit={onSubmit} title={"form"}>
        <TextField
            required
            label="Name"
            value={name}
            onInput={(e: React.ChangeEvent<HTMLInputElement>)=> setName(e.target.value)}
            />
        <TextField
            required
            label="Airframe"
            value={airframe}
            onInput={(e: React.ChangeEvent<HTMLInputElement>)=> setAirfram(e.target.value)}
            />
        <TextField
            required
            label="Pilot"
            value={pilot}
            onInput={(e: React.ChangeEvent<HTMLInputElement>)=> setPilot(e.target.value)}
            />
            <div>
            <Button variant="contained" type="submit">Save</Button>
            </div>
            </form>
</>
    )
}