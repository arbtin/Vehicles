import { describe, expect, test } from 'vitest'
import { render, screen, waitFor, within } from '@testing-library/react'
import App from '../App'

import * as api from '../api/AircraftApi.ts'
import { Aircraft } from '../types/Aircraft.ts'
import AircraftList from '../components/AircraftList.tsx'
//import { all } from 'axios'


describe('when the Aircraft module is rendered', () => {
    test('should display the header', () => {
        render(<App />)
        expect(screen.getByText('Aircraft')).toBeDefined()
    })

    test('should display list of aircraft', async () => {
        render(<App />)

        const listElements = await screen.findAllByRole('row')
        expect(listElements).toHaveLength(1)

        expect(within(listElements[0]).getByText('Monoplane'))
        expect(within(listElements[0]).getByText('egor'))
    })
})


describe('When responder page is render', () => {
    const getAllSpy = vi.spyOn(api, 'getAllAircraft')
    const sampleRequests: Aircraft[] = [
        {
            id: 5,
            airframe: 'biplane',
            pilot: 'egor',
        },
        {
            id: 2,
            airframe: 'monoplane',
            pilot: 'mike',
        },
    ]

    it('should render a table', () => {
        render(<App />)
        const tableElement = screen.getByRole('table')
        expect(tableElement).toBeVisible()
    })
    it('should make an API call to request all aircraft on page load', () => {
        getAllSpy.mockResolvedValue(sampleRequests)
        render(<App />)
        expect(getAllSpy).toBeCalledTimes(2)
    })
    it('should render the items in the API call', async () => {
        getAllSpy.mockResolvedValue(sampleRequests)
        render(<App />)
        const tableElement = await screen.findByRole('table')

        await waitFor(() => {
            expect(tableElement).toHaveAttribute('scope', '2')
        })
        const allRows = screen.getAllByRole('row')
        expect(allRows).toHaveLength(2)
    })
})
