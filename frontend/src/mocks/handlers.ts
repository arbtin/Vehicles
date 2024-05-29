import { http, HttpResponse } from 'msw'

const testAirframe = {
    id: 1,
    airframe: 'Monoplane',
    pilot: 'egor',
    crew_chief_id: null,
}

export const handlers = [
    http.get('/api/aircraft', () => {
        return HttpResponse.json([testAirframe])
    }),
]