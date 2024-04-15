import { useState, useEffect } from 'react';

const useFetch = (fetchCallback: () => Promise<Response>) => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const response = await fetchCallback();
                const responseData = await response.json();
                setData(responseData);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchData();
    }, [fetchCallback]);

    return { data, loading, error };
};

export default useFetch;
