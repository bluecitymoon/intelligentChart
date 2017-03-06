'use strict';

describe('Controller Tests', function() {

    describe('PersonSocialMedia Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonSocialMedia, MockPerson, MockSocialMediaType, MockSocialMediaAttributeName;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonSocialMedia = jasmine.createSpy('MockPersonSocialMedia');
            MockPerson = jasmine.createSpy('MockPerson');
            MockSocialMediaType = jasmine.createSpy('MockSocialMediaType');
            MockSocialMediaAttributeName = jasmine.createSpy('MockSocialMediaAttributeName');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonSocialMedia': MockPersonSocialMedia,
                'Person': MockPerson,
                'SocialMediaType': MockSocialMediaType,
                'SocialMediaAttributeName': MockSocialMediaAttributeName
            };
            createController = function() {
                $injector.get('$controller')("PersonSocialMediaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personSocialMediaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
