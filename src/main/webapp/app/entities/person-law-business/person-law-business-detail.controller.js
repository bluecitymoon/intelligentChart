(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonLawBusinessDetailController', PersonLawBusinessDetailController);

    PersonLawBusinessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonLawBusiness', 'Person', 'LawBusinessType'];

    function PersonLawBusinessDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonLawBusiness, Person, LawBusinessType) {
        var vm = this;

        vm.personLawBusiness = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personLawBusinessUpdate', function(event, result) {
            vm.personLawBusiness = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
