(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SexDetailController', SexDetailController);

    SexDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sex'];

    function SexDetailController($scope, $rootScope, $stateParams, previousState, entity, Sex) {
        var vm = this;

        vm.sex = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:sexUpdate', function(event, result) {
            vm.sex = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
