import { Button, TextField } from '@mui/material'
import React, { useState } from 'react'
import { newAircraft } from '../../src/api/AircraftApi.ts'
import { useNavigate } from 'react-router-dom'

export default function Create(): any {
    const redirect = useNavigate()

    const [name, setName] = useState('')
    const [airframe, setAirfram] = useState('')
    const [pilot, setPilot] = useState('')

    const aircraft = {
        name: name,
        airframe: airframe,
        pilot: pilot,
    }

    const onFormSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        saveAircraft(aircraft)
        redirect('/')
    }

    return (
        <>
            <h2>New Aircraft</h2>
            <div className="border-b border-gray-900/10 pb-12">
                <form onSubmit={onFormSubmit}>
                    <div className="sm:col-span-4">
                        <label className="test">Airframe</label>
                        <input type="text" className="" required
                               onChange={e => setAirframe(e.target.value)}
                               value={airframe}
                        ></input>
                    </div>
                    <div className="sm:col-span-4">
                        <label className="fd">Description</label>
                        <textarea className="" rows={3} required
                                  onChange={e => setDescription(e.target.value)}
                                  value={description}
                        ></textarea>
                    </div>
                    <button type="button"
                            onClick={submitAirframe}
                            className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Add
                        Todo
                    </button>
                </form>
            </div>
        </>
    )
}