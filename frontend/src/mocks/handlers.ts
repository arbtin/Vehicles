import { http, HttpResponse } from 'msw'

const testAirframe = {
    id: 1,
    airframe: 'Monoplane',
    pilot: 'egor',
}

export const handlers = [
    http.get('/api/v1/aircraft', () => {
        return HttpResponse.json([testAirframe])
    }),
]