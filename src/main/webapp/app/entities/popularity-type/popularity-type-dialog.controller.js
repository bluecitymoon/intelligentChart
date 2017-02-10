(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PopularityTypeDialogController', PopularityTypeDialogController);

    PopularityTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PopularityType', 'PersonPopularity'];

    function PopularityTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PopularityType, PersonPopularity) {
        var vm = this;

        vm.popularityType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.personpopularities = PersonPopularity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.popularityType.id !== null) {
                PopularityType.update(vm.popularityType, onSaveSuccess, onSaveError);
            } else {
                PopularityType.save(vm.popularityType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:popularityTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
