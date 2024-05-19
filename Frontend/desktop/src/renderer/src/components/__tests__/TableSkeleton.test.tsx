import { render, screen } from '@testing-library/react';
import TableSkeleton from '../TableSkeleton'; // assuming this test file is in the same directory as TableSkeleton.js

describe('TableSkeleton component', () => {
    test('renders the skeleton with correct structure and classes', () => {
        render(<TableSkeleton />);
        const tableSkeleton = screen.getByTestId('table-skeleton');
        expect(tableSkeleton).toBeInTheDocument();
    });
});
