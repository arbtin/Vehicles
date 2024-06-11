import { afterAll, afterEach, beforeAll, describe, expect, test } from 'vitest'
import { server } from '../../mocks/node.ts'
import { http, HttpResponse } from 'msw'
import { getAllAircraft, newAircraft } from '../AircraftApi.ts'
import { Aircraft } from '../../types/Aircraft.ts'
import { DefaultAircraft } from '../../types/AircraftBuilder.ts'

describe('Aircraft APIs', () => {
    beforeAll(() => server.listen())
    afterEach(() => server.resetHandlers())
    afterAll(() => server.close())
    describe('Get All Aircraft', () => {
        test('should get all aircraft', async () => {
            const mockResponse = [DefaultAircraft]

            server.use(
                http.get('/api/aircraft', () => {
                    return HttpResponse.json([...mockResponse])
                }),
            )

            const actual = await getAllAircraft()
            expect(actual).toEqual(mockResponse)
        })

        test('should reject promise on bad response', async () => {
            server.use(
                http.get('/api/aircraft', async () => {
                    return HttpResponse.error()
                }),
            )
            const expected = new Error('Failed to fetch aircraft')

            await expect(getAllAircraft()).rejects.toThrow(expected)
        })
    })
    describe('Saving an Aircraft', () => {
        test('should save a valid aircraft', async () => {

            const mockResponse = {...DefaultAircraft, id: 1}

            server.use(
                http.post('/api/aircraft', async ({request}) => {
                    const data = await request.json() as Aircraft
                    return HttpResponse.json({...data, id: 1})
                })
            )

            const actual = await newAircraft(DefaultAircraft);

            expect(actual).toEqual(mockResponse);
        })

        test('should reject promise on bad response', async () => {
            const expected = new Error('Unable to create new aircraft');

            server.use(
                http.post('/api/aircraft', async () => {
                    return HttpResponse.error();
                })
            )

            await expect(newAircraft(DefaultAircraft)).rejects.toThrow(expected);
        })
    })
})