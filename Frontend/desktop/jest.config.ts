import type { Config } from 'jest';

const config: Config = {
    verbose: true,
    testEnvironment: 'jsdom',
    setupFilesAfterEnv: ['<rootDir>/jest-setup.ts'],
    moduleNameMapper: {
        '^@renderer/(.*)$': '<rootDir>/src/renderer/src/$1'
    },
    collectCoverage: true,
    // collectCoverageFrom: ['<rootDir>/src/renderer/src'],
    coverageReporters: ['text', 'json', 'html']
    // coverageDirectory: '/tests/unit/coverage'
};

export default config;
