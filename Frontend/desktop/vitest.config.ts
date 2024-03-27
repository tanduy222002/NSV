import { defineConfig } from 'vitest/config';

export default defineConfig({
    test: {
        globals: true,
        environment: 'jsdom',
        include: ['**/__tests__/*.{js,tsx,ts,jsx}'],
        setupFiles: ['./setup.ts']
    }
});
