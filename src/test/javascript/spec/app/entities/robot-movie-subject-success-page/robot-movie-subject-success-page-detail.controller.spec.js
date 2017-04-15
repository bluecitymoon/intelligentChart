'use strict';

describe('Controller Tests', function() {

    describe('RobotMovieSubjectSuccessPage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRobotMovieSubjectSuccessPage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRobotMovieSubjectSuccessPage = jasmine.createSpy('MockRobotMovieSubjectSuccessPage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RobotMovieSubjectSuccessPage': MockRobotMovieSubjectSuccessPage
            };
            createController = function() {
                $injector.get('$controller')("RobotMovieSubjectSuccessPageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:robotMovieSubjectSuccessPageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
