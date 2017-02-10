(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeGroupDialogController', PrizeGroupDialogController);

    PrizeGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrizeGroup'];

    function PrizeGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrizeGroup) {
        var vm = this;

        vm.prizeGroup = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.prizeGroup.id !== null) {
                PrizeGroup.update(vm.prizeGroup, onSaveSuccess, onSaveError);
            } else {
                PrizeGroup.save(vm.prizeGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:prizeGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
