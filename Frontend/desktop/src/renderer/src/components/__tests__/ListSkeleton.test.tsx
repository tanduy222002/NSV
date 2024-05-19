import { render } from '@testing-library/react';
import ListSkeleton from '../ListSkeleton'; // Assuming ListSkeleton component is in the same directory

describe('ListSkeleton Component', () => {
    it('renders without crashing', () => {
        render(<ListSkeleton />);
    });

    it('renders skeleton elements correctly', () => {
        const { getByRole } = render(<ListSkeleton />);

        // Check if the main container has the role "status"
        const container = getByRole('status');
        expect(container).toBeInTheDocument();

        const loadingSpan = container.querySelector('span.sr-only');
        expect(loadingSpan).toBeInTheDocument();
        expect(loadingSpan?.textContent).toBe('Loading...'); // Assuming the content of the span is "Loading..."
    });
});
