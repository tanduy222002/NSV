import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import DataField from '../DataField';

describe('DataField Component', () => {
    test('renders correctly with all props', () => {
        render(
            <DataField
                icon={<span>Icon</span>}
                name="Test Name"
                value="Test Value"
                defaultValue="Default Value"
                disabled={false}
                textColor="text-red-500"
            />
        );

        expect(screen.getByText('Test Name')).toBeInTheDocument();
        expect(screen.getByText('Test Value')).toBeInTheDocument();
        expect(screen.getByText('Test Value')).toHaveClass('text-red-500');
        expect(screen.getByText('Icon')).toBeInTheDocument();
    });

    test('renders correctly when disabled', () => {
        render(
            <DataField
                icon={<span>Icon</span>}
                name="Test Name"
                defaultValue="Default Value"
                disabled={true}
            />
        );

        expect(screen.getByText('Test Name')).toBeInTheDocument();
        expect(screen.getByText('Default Value')).toBeInTheDocument();
    });

    test('renders value if provided, otherwise defaultValue', () => {
        const { rerender } = render(
            <DataField
                name="Test Name"
                defaultValue="Default Value"
                disabled={false}
            />
        );

        expect(screen.getByText('Default Value')).toBeInTheDocument();

        rerender(
            <DataField
                name="Test Name"
                value="Test Value"
                defaultValue="Default Value"
                disabled={false}
            />
        );

        expect(screen.getByText('Test Value')).toBeInTheDocument();
        expect(screen.queryByText('Default Value')).not.toBeInTheDocument();
    });
});
