import { describe, expect, test } from 'vitest'
import {render, screen, within} from '@testing-library/react'
import App from '../App'

describe('when the app is rendered', () => {
    test('should display the header', () => {
        render(<App />)
        expect(screen.getByText('Aircraft')).toBeDefined()
    })

    test('should display list of aircraft', async () => {
        render(<App />)

        const listElements = await screen.findAllByRole('listitem')
        expect(listElements).toHaveLength(1)

        expect(within(listElements[0]).getByText('Monoplane'))
        expect(within(listElements[0]).getByText('Egor'))
    })
})