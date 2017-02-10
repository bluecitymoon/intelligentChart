(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeLevelDialogController', PrizeLevelDialogController);

    PrizeLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrizeLevel'];

    function PrizeLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrizeLevel) {
        var vm = this;

        vm.prizeLevel = entity;
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
            if (vm.prizeLevel.id !== null) {
                PrizeLevel.update(vm.prizeLevel, onSaveSuccess, onSaveError);
            } else {
                PrizeLevel.save(vm.prizeLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:prizeLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
