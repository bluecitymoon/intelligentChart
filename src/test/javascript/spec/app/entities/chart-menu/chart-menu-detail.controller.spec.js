'use strict';

describe('Controller Tests', function() {

    describe('ChartMenu Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChartMenu, MockChart, MockMenu;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChartMenu = jasmine.createSpy('MockChartMenu');
            MockChart = jasmine.createSpy('MockChart');
            MockMenu = jasmine.createSpy('MockMenu');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ChartMenu': MockChartMenu,
                'Chart': MockChart,
                'Menu': MockMenu
            };
            createController = function() {
                $injector.get('$controller')("ChartMenuDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:chartMenuUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
