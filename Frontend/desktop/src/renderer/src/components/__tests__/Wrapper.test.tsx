import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Wrapper from '../Wrapper';

describe('Wrapper component', () => {
    test('renders without crashing', () => {
        render(<Wrapper gap="sm">Test</Wrapper>);
        const wrapperElement = screen.getByText('Test');
        expect(wrapperElement).toBeInTheDocument();
    });

    test('renders children with small gap', () => {
        render(
            <Wrapper gap="sm">
                <div>Child 1</div>
                <div>Child 2</div>
            </Wrapper>
        );

        const child1Element = screen.getByText('Child 1');
        const child2Element = screen.getByText('Child 2');

        expect(child1Element).toBeInTheDocument();
        expect(child2Element).toBeInTheDocument();

        expect(child1Element.parentElement).toHaveClass('gap-2');
        expect(child2Element.parentElement).toHaveClass('gap-2');
    });

    test('renders children with medium gap', () => {
        render(
            <Wrapper gap="md">
                <div>Child 1</div>
                <div>Child 2</div>
            </Wrapper>
        );

        const child1Element = screen.getByText('Child 1');
        const child2Element = screen.getByText('Child 2');

        expect(child1Element).toBeInTheDocument();
        expect(child2Element).toBeInTheDocument();

        expect(child1Element.parentElement).toHaveClass('gap-4');
        expect(child2Element.parentElement).toHaveClass('gap-4');
    });

    test('renders children with large gap', () => {
        render(
            <Wrapper gap="lg">
                <div>Child 1</div>
                <div>Child 2</div>
            </Wrapper>
        );

        const child1Element = screen.getByText('Child 1');
        const child2Element = screen.getByText('Child 2');

        expect(child1Element).toBeInTheDocument();
        expect(child2Element).toBeInTheDocument();

        expect(child1Element.parentElement).toHaveClass('gap-6');
        expect(child2Element.parentElement).toHaveClass('gap-6');
    });
});
