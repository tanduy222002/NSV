/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        './src/renderer/index.html',
        './src/renderer/src/**/*.{js,ts,jsx,tsx}',
        './node_modules/react-tailwindcss-datepicker/dist/index.esm.js'
    ],
    theme: {},
    plugins: []
};
