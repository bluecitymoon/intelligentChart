(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('FansPurchasingSection', FansPurchasingSection);

    FansPurchasingSection.$inject = ['$resource'];

    function FansPurchasingSection ($resource) {
        var resourceUrl =  'api/fans-purchasing-sections/:id';
        var findByPersonUrl =  'api/fans-purchasing-sections/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
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
