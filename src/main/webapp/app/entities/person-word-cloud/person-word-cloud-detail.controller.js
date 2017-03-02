(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonWordCloudDetailController', PersonWordCloudDetailController);

    PersonWordCloudDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonWordCloud', 'Person', 'WordCloud'];

    function PersonWordCloudDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonWordCloud, Person, WordCloud) {
        var vm = this;

        vm.personWordCloud = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personWordCloudUpdate', function(event, result) {
            vm.personWordCloud = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
