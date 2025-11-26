import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';

const BASE_URL = 'http://localhost:8082/api/v1';

const errorRate = new Counter('error_rate');

export const options = {
    scenarios: {
        search_models: {
            executor: 'constant-arrival-rate',
            rate: 50,
            timeUnit: '2s',
            duration: '2m',
            preAllocatedVUs: 10,
            maxVUs: 100,
            exec: 'searchModels',
        },
        add_to_cart: {
            executor: 'constant-arrival-rate',
            rate: 10,
            timeUnit: '2s',
            duration: '2m',
            preAllocatedVUs: 5,
            maxVUs: 50,
            exec: 'addToCart',
        },
        login: {
            executor: 'constant-arrival-rate',
            rate: 10,
            timeUnit: '5s',
            duration: '2m',
            preAllocatedVUs: 5,
            maxVUs: 50,
            exec: 'login',
        },
    },
    thresholds: {
        'http_req_duration{scenario:search_models}': ['p(95)<500'],
        'http_req_duration{scenario:add_to_cart}': ['p(95)<800'],
        'error_rate': ['count<20'],
    },
};

export function searchModels() {
    const searchTerms = ['autem', 'qui', 'fuga', 'iste', 'aut'];
    const randomTerm = searchTerms[Math.floor(Math.random() * searchTerms.length)];

    const res = http.get(`${BASE_URL}/models/search?q=${randomTerm}`);

    const success = check(res, {
        'search status is 200': (r) => r.status === 200,
    });
    if (!success) {
        errorRate.add(1);
    }
    sleep(1);
}

export function addToCart() {
    const payload = JSON.stringify({
        modelId: '3c4aa871-d97b-4103-ac09-bfae2a36ca87',
        quantity: 1
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        }
    };

    const res = http.post(`${BASE_URL}/carts/items`, payload, params);

    const success = check(res, {
        'add to cart status is 200': (r) => r.status === 200,
    });
    if (!success) {
        errorRate.add(1);
    }
    sleep(2);
}

export function login() {
    const payload = JSON.stringify({
        email: 'artist@example.com',
        password: 'Strong@ss123'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        }
    };

    const res = http.post(`${BASE_URL}/auth/login`, payload, params);

    const success = check(res, {
        'login status is 200': (r) => r.status === 200,
    });
    if (!success) {
        errorRate.add(1);
    }
    sleep(2);
}