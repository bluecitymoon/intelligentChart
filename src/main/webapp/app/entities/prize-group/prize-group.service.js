(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PrizeGroup', PrizeGroup);

    PrizeGroup.$inject = ['$resource'];

    function PrizeGroup ($resource) {
        var resourceUrl =  'api/prize-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
