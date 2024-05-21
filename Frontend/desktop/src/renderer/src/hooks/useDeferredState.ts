import { useState, useEffect, useRef, useCallback } from 'react';

export function useDeferredState(initialValue) {
    const [state, setState] = useState(initialValue);
    const timeoutRef = useRef<NodeJS.Timeout | null>(null);

    const setDeferredState = useCallback((value) => {
        if (timeoutRef.current) {
            clearTimeout(timeoutRef.current);
        }
        timeoutRef.current = setTimeout(() => {
            setState(value);
        }, 350);
    }, []);

    useEffect(() => {
        // Clean up the timeout if the component unmounts
        return () => {
            if (timeoutRef.current) {
                clearTimeout(timeoutRef.current);
            }
        };
    }, []);

    return [state, setDeferredState];
}
