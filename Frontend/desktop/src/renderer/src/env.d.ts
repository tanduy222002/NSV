/// <reference types="vite/client" />

interface ImportMeta {
    readonly env: ImportMetaEnv;
}

interface ImportMetaEnv {
    readonly RENDERER_VITE_SERVER: string;
    // more env variables...
}
