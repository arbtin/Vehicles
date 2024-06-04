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


                <div className='border-b border-gray-900/10 pb-12'>
                  <form>
                    <div className='sm:col-span-4'>
                      <label className='test'>Assigned</label>
                      <input type='text' className='' required
                      onChange={e => setAssigned(e.target.value)}
                      value={assigned}
                      ></input>
                    </div>
                    <div className='sm:col-span-4'>
                      <label className='fd'>Description</label>
                      <textarea className='' rows={3} required
                      onChange={e => setDescription(e.target.value)}
                      value={description}
                      ></textarea>
                    </div>
                    <button type='button'
                     onClick={submitTodo}
                     className='rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600'>Add Todo</button>
                  </form>
                </div>
</>
    )
}